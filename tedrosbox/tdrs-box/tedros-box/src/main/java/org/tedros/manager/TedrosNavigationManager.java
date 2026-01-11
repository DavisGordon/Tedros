package org.tedros.manager;

import java.util.Optional;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.tedros.TWindow;
import org.tedros.core.ITModule;
import org.tedros.core.context.Page;
import org.tedros.core.context.Pages;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.TedrosBoxBreadcrumbBar;
import org.tedros.core.ux.ITWindow;
import org.tedros.fx.modal.TConfirmMessageBox;
import org.tedros.util.TLoggerUtil;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class TedrosNavigationManager {

    private Stack<Page> history;
    private Stack<Page> forwardHistory;
    private boolean fromForwardOrBackButton;
    private boolean changingPage;

    private Pages pages;
    private Page currentPage;
    private String currentPagePath;

    @SuppressWarnings("rawtypes")
	private TreeView menuTree;
    private Pane pageArea;
    private TedrosBoxBreadcrumbBar breadcrumbBar;
    private StringProperty historySize;
    private StringProperty forwardSize;

    @SuppressWarnings("rawtypes")
	public TedrosNavigationManager(TreeView menuTree, Pane pageArea, TedrosBoxBreadcrumbBar breadcrumbBar,
            StringProperty historySize, StringProperty forwardSize) {
        this.menuTree = menuTree;
        this.pageArea = pageArea;
        this.breadcrumbBar = breadcrumbBar;
        this.historySize = historySize;
        this.forwardSize = forwardSize;

        history = new Stack<>();
        forwardHistory = new Stack<>();
        changingPage = false;
        fromForwardOrBackButton = false;

        initListeners();
    }

    private void initListeners() {
        TedrosContext.pageProperty().addListener((a, o, n) -> goToPage(n, TedrosContext.isPageAddHistory(),
                TedrosContext.isPageForce(), TedrosContext.isPageSwapViews()));

        TedrosContext.pagePathProperty().addListener((a, o, n) -> {
            if (StringUtils.isNotBlank(n) && pages != null)
                goToPage(pages.getPage(n), true, TedrosContext.isPageForce(), true);
        });

        TedrosContext.detachedViewProperty().addListener((a, o, n) -> {
            if (n != null) {
                detachView(n);
            }
        });
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public Pages getPages() {
        return pages;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void goToPage(String pagePath) {
        if (pages != null)
            TedrosContext.setPageProperty(pages.getPage(pagePath), true, false, true);
    }

    public void goToPage(String pagePath, boolean force) {
        if (pages != null)
            TedrosContext.setPageProperty(pages.getPage(pagePath), true, force, true);
    }

    public void goToPage(Page page) {
        TedrosContext.setPageProperty(page, true, false, true);
    }

    @SuppressWarnings("unchecked")
    private void goToPage(Page page, boolean addHistory, boolean force, boolean swapViews) {
        if (page == null)
            return;
        if (!force && page == currentPage)
            return;

        Node currentView = TedrosContext.getView();
        ITModule itModule = null;

        Optional<ITWindow> optSelectedModuleWindowed = getPageWindowed(page);
        Optional<ITWindow> optCurrentDetachedView = getCurrentDetachedView();

        boolean isWindowed = optSelectedModuleWindowed.isPresent();
        if (isWindowed) {
            ITWindow window = optSelectedModuleWindowed.get();
            TedrosContext.getWindows().remove(window);
        }

        if (currentView != null && currentView instanceof ITModule m)
            itModule = m;
        else if (currentView != null && currentView instanceof ScrollPane scroll
                && scroll.getContent() instanceof ITModule m)
            itModule = m;

        if (itModule != null) {
            String msg = itModule.tCanStop();
            if (msg == null) {
                callPage(page, addHistory, force, swapViews);
            } else {
                if (!optCurrentDetachedView.isPresent()) {
                    ChangeListener<Number> chl = (a0, a1, a2) -> {
                        TedrosContext.hideModal();
                        if (a2.equals(1)) {
                            callPage(page, addHistory, force, swapViews);
                        } else {
                            menuTree.getSelectionModel().select(currentPage);
                        }
                    };

                    TConfirmMessageBox confirm = new TConfirmMessageBox(msg);
                    confirm.tConfirmProperty().addListener(chl);
                    TedrosContext.showModal(confirm);
                } else {
                    callPage(page, addHistory, force, swapViews);
                }
            }
        } else
            callPage(page, addHistory, force, swapViews);
    }

    private Optional<ITWindow> getPageWindowed(Page page) {
        Node moduleSelected = page.getModule();
        Optional<ITWindow> optSelectedModuleWindowed = TedrosContext.getWindows().stream()
                .filter(p -> {

                    Node wView = p.getView();
                    if (wView instanceof ScrollPane sp)
                        wView = sp.getContent();

                    return wView.equals(moduleSelected);
                })
                .findFirst();
        return optSelectedModuleWindowed;
    }

    private Optional<ITWindow> getCurrentDetachedView() {
        Node detachedView = TedrosContext.getDetachedView();
        Optional<ITWindow> optCurrentDetachedView = detachedView != null
                ? TedrosContext.getWindows().stream()
                        .filter(p -> {

                            Node wView = p.getView();
                            if (wView instanceof ScrollPane sp)
                                wView = sp.getContent();
                            Node moduleView = detachedView.getParent();
                            return wView.equals(moduleView);
                        })
                        .findFirst()
                : Optional.empty();
        return optCurrentDetachedView;
    }

    @SuppressWarnings({ "unchecked"})
    private void callPage(Page page, boolean addHistory, boolean force, boolean swapViews) {

        if (page == null)
            return;
        if (!force && page == currentPage)
            return;

        changingPage = true;
        if (swapViews) {
            boolean created = false;
            Node view = page.getModule();
            if (view == null) {
                view = page.createModule();
                created = true;
            }

            if (view == null)
                view = new Region();

            if (force || view != TedrosContext.getView()) {

                Node content;
                if (view instanceof ITModule itModule) {

                    if (created)
                        itModule.tStart();

                    ScrollPane scrollPane = new ScrollPane();
                    scrollPane.setContent(view);
                    scrollPane.setFitToWidth(true);
                    scrollPane.setFitToHeight(true);
                    scrollPane.setMinWidth(725);
                    scrollPane.getStyleClass().add("noborder-scroll-pane");
                    content = scrollPane;
                } else {
                    content = view;
                    content.setStyle("-fx-background-color: transparent;");
                }

                pageArea.getChildren().setAll(content);
                TedrosContext.setView(view);

                addHistory(addHistory, page);
            }
        }

        currentPage = page;
        currentPagePath = page.getPath();

        for (Page p = page; p != null; p = (Page) p.getParent())
            p.setExpanded(true);

        menuTree.getSelectionModel().select(page);
        breadcrumbBar.setPath(currentPagePath);
        changingPage = false;
    }

    public void detachView(Node view) {
        if (TedrosContext.getWindows().stream().anyMatch(p -> p.getView().equals(view))) {
            return;
        }

        Node currentNode = pageArea.getChildren().get(0);
        if (view.getParent() != null) {
            Parent parent = view.getParent();
            if (parent instanceof ITModule itModule && currentNode instanceof ScrollPane scroll
                    && scroll.getContent() == itModule) {
                pageArea.getChildren().clear();
            }
        }

        if (!history.isEmpty()) {
            Page page = history.stream()
                    .filter(p -> p.getModule() == view.getParent())
                    .findFirst()
                    .orElse(null);

            history.removeIf(p -> p == page);
            clearForward(page);
            historySize.setValue(String.valueOf(history.size()));
            forwardSize.setValue(String.valueOf(forwardHistory.size()));
        }

        ITWindow window = new TWindow(currentNode);
        TedrosContext.getWindows().add(window);

        Object obj = menuTree.getTreeItem(0).getValue();
        TedrosContext.setPagePathProperty(obj.toString(), false, true, true);
        TedrosContext.detachView(null);
    }

    private void addHistory(boolean addHistory, Page page) {
        if (addHistory && currentPage != null
                && currentPage.getModule() instanceof ITModule) {
            Page p = currentPage;
            if (!history.contains(p)) {
                history.push(p);
                resizeHistory();
                if (page.getModule() instanceof ITModule)
                    clearForward(page);
                historySize.setValue(String.valueOf(history.size()));
                forwardSize.setValue(String.valueOf(forwardHistory.size()));
            }
        }
    }

    private void resizeHistory() {
        if (history.size() >= TedrosContext.getTotalPageHistory()) {
            Page rem = history.remove(0);
            Node n = rem.getModule();
            Optional<ITWindow> pageWindowed = getPageWindowed(rem);
            if (n instanceof ITModule itModule && !pageWindowed.isPresent())
                itModule.tStop();
        }
    }

    public boolean isFromForwardOrBackButton() {
        return fromForwardOrBackButton;
    }

    public void back() {
        fromForwardOrBackButton = true;
        if (!history.isEmpty()) {
            Page prevPage = history.pop();
            if (currentPage != null && currentPage.getModule() instanceof ITModule
                    && !forwardHistory.contains(currentPage)) {
                forwardHistory.push(currentPage);
            }
            historySize.setValue(String.valueOf(history.size()));
            forwardSize.setValue(String.valueOf(forwardHistory.size()));
            TedrosContext.setPageProperty(prevPage, false, false, true);
        }
        fromForwardOrBackButton = false;
        printHistory();
    }

    public void forward() {
        fromForwardOrBackButton = true;
        if (!forwardHistory.isEmpty()) {
            Page prevPage = forwardHistory.pop();
            if (currentPage != null && currentPage.getModule() instanceof ITModule && !history.contains(currentPage)) {
                history.push(currentPage);
                resizeHistory();
            }
            historySize.setValue(String.valueOf(history.size()));
            forwardSize.setValue(String.valueOf(forwardHistory.size()));
            TedrosContext.setPageProperty(prevPage, false, false, true);
        }
        fromForwardOrBackButton = false;
        printHistory();
    }

    public void clearPageHistory() {
        history.stream().forEach(p -> {
            Node n = p.getModule();
            Optional<ITWindow> pageWindowed = getPageWindowed(p);
            if (n != null && n instanceof ITModule itModule && !pageWindowed.isPresent()) {
                itModule.tStop();
            }
        });
        history.clear();
        historySize.setValue(String.valueOf(history.size()));
        clearForward(null);
    }

    private void printHistory() {
        StringBuilder builder = new StringBuilder();
        builder.append("   HISTORY = ");
        for (Page page : history) {
            builder.append(page.getName() + "->");
        }
        if (currentPage != null)
            builder.append("   [" + currentPage.getName() + "]");

        for (Page page : forwardHistory) {
            builder.append(page.getName() + "->");
        }
        TLoggerUtil.info(getClass(), builder.toString());
    }

    private void clearForward(Page page) {
        forwardHistory.stream().forEach(p -> {
            Node n = p.getModule();
            Optional<ITWindow> pageWindowed = getPageWindowed(p);
            if (n != null && n instanceof ITModule itModule
                    && !pageWindowed.isPresent()
                    && (page == null || (page != null && page != p))) {
                itModule.tStop();
            }
        });
        forwardHistory.clear();
        forwardSize.setValue(String.valueOf(forwardHistory.size()));
    }

    public void reload() {
        TedrosContext.setPageProperty(currentPage, false, true, true);
    }

    public void reset() {
        currentPage = null;
        currentPagePath = "";
        changingPage = false;
        pageArea.getChildren().clear();
        breadcrumbBar.setPath("");
        clearPageHistory();
    }

    public boolean isChangingPage() {
        return changingPage;
    }
}
