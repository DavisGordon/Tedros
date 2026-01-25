package org.tedros.tools.module.ai.component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.ai.TFunctionHelper;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.service.AiServiceProvider;
import org.tedros.ai.service.AiTerosServiceFactory;
import org.tedros.ai.service.IAiTerosService;
import org.tedros.ai.web.TerosWebViewBridge;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.ITProgressIndicator;
import org.tedros.core.control.TProgressIndicator;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.domain.TSystemPropertie;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.fx.component.ITComponent;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.form.TFieldBox;
import org.tedros.fx.layout.TToolBar;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.process.TProcess;
import org.tedros.fx.process.TTaskImpl;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TedrosFolder;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class AiProviderComparisonComponent extends VBox implements ITComponent {
	
	private static final String SYSTEM_PROMPT = """
		    You are Teros, the official AI assistant of the Tedros System.
		    
		    Current Context: **COMPARISON & ANALYSIS MODE**
		    You are operating inside a specific comparison component where the user tests different AI providers.
		    
		    ==================================================
		    OUTPUT FORMAT (CRITICAL)
		    ==================================================
		    1. **RAW HTML ONLY**: Return ONLY the HTML string.
		    2. **NO MARKDOWN**: Never use code blocks (e.g., ```html).
		    3. **NO ESCAPING**: Do not escape tags (use <div>, not &lt;div&gt;).
		    4. **STRUCTURE**: Do not use <html>, <head> or <body> tags. Start directly with content (<h3>, <p>, <div>).
		    5. **STYLING**: Use inline CSS only. Dark text (#333) on transparent/light background.
		    
		    ==================================================
		    FUNCTION CALLING & SECURITY PROTOCOLS
		    ==================================================
		    You have access to system tools, but strict rules apply in this mode:
		    
		    1. **READ-ONLY ALLOWED**: You MAY use functions to fetch data, list views, or query system info.
		    2. **NAVIGATION FORBIDDEN**: You MUST NOT call functions that open windows, switch views, or navigate the UI (e.g., DO NOT use `callUpViewFunction`).
		    3. **WRITE FORBIDDEN**: DO NOT use functions that create files or insert records.
		    
		    If the user asks to "Open Screen X":
		    - Do NOT call the view function.
		    - Instead, fetch the data regarding "Screen X" and display the *information* directly in the HTML response.
		    - Explain that in Comparison Mode, navigation is disabled.
		    
		    ==================================================
		    RESPONSE LOGIC
		    ==================================================
		    1. Analyze the user request.
		    2. If tools are needed to GET data, call them.
		    3. Process the result.
		    4. Format the final answer in clean, semantic HTML5.
		    5. Ensure the response language matches the user's input (PT/EN).
		    
		    ==================================================
		    ERROR HANDLING
		    ==================================================
		    If a tool fails or requires navigation parameters you cannot fulfill:
		    - Return an HTML error message (<div style='color:red'>...</div>).
		    - Suggest the user try the main chat for operational tasks.
		    """;

    private TextArea promptArea;
    private TButton sendButton;
    private TButton clearButton;

    private WebView openaiWebView;
    private WebView grokWebView;
    private WebView geminiWebView;

    private TerosWebViewBridge openaiBridge;
    private TerosWebViewBridge grokBridge;
    private TerosWebViewBridge geminiBridge;

    private TerosService openaiService;
    private TerosService grokService;
    private TerosService geminiService;
    
    private ITProgressIndicator openaiProgressIndicator;
    private ITProgressIndicator grokProgressIndicator;
    private ITProgressIndicator geminiProgressIndicator;
    
    private String openaiKey;
    private String openaiModel;

    private String grokKey;
    private String grokModel;

    private String geminiKey;
    private String geminiModel;
    
    @SuppressWarnings("rawtypes")
	private ITView view;

    public AiProviderComparisonComponent() {
        super(10);
        setPadding(new Insets(10));
        setFillWidth(true);
    }

    @Override
    public void tInitializeComponent(ITComponentDescriptor descriptor) {
        initializeUI();
        Platform.runLater(() -> {
            try {
            	
            	getProviderProperties();
            	
                String templateUrl = "file:" + TedrosFolder.MODULE_FOLDER.getFullPath() + "TCORE_19780222"
                        + java.io.File.separator
                        + "teros_ia_response.html";

                openaiWebView.getEngine().setJavaScriptEnabled(true);
                openaiWebView.getEngine().load(templateUrl);
                openaiBridge = new TerosWebViewBridge(openaiWebView);

                grokWebView.getEngine().setJavaScriptEnabled(true);
                grokWebView.getEngine().load(templateUrl);
                grokBridge = new TerosWebViewBridge(grokWebView);

                geminiWebView.getEngine().setJavaScriptEnabled(true);
                geminiWebView.getEngine().load(templateUrl);
                geminiBridge = new TerosWebViewBridge(geminiWebView);
                
                // OpenAI Service
                
                openaiService = new TerosService();
                openaiService.buildIaTerosService(openaiKey, openaiModel, SYSTEM_PROMPT, AiServiceProvider.OPENAI);
                openaiProgressIndicator.bind(openaiService.runningProperty());
                
                openaiService.onFailedProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(() -> {
                	setLoadingState(false);
            	    Platform.runLater(() -> showAlert("Erro na análise: " + openaiService.getException().getMessage()));
            	}));
            	
                openaiService.setOnSucceeded(e -> {
                	String htmlMessage = openaiService.getValue();
                	updateWebViewContent(openaiBridge, htmlMessage);
                	setLoadingState(false);
            	});
                
                // Grok Service
                
                grokService = new TerosService();
                grokService.buildIaTerosService(grokKey, grokModel, SYSTEM_PROMPT, AiServiceProvider.GROK);
                grokProgressIndicator.bind(grokService.runningProperty());
                
                grokService.onFailedProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(() -> {
                	setLoadingState(false);
            	    Platform.runLater(() -> showAlert("Erro na análise: " + grokService.getException().getMessage()));
            	}));
            	
                grokService.setOnSucceeded(e -> {
                	String htmlMessage = grokService.getValue();
                	updateWebViewContent(grokBridge, htmlMessage);
                	setLoadingState(false);
            	});
                
                // Gemini Service
                
                geminiService = new TerosService();
                geminiService.buildIaTerosService(geminiKey, geminiModel, SYSTEM_PROMPT, AiServiceProvider.GEMINI);
                geminiProgressIndicator.bind(geminiService.runningProperty());
                
                geminiService.onFailedProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(() -> {
                	setLoadingState(false);
            	    Platform.runLater(() -> showAlert("Erro na análise: " + geminiService.getException().getMessage()));
            	}));
            	
                geminiService.setOnSucceeded(e -> {
                	String htmlMessage = geminiService.getValue();
                	updateWebViewContent(geminiBridge, htmlMessage);
                	setLoadingState(false);
            	});

            } catch (Exception e) {
                TLoggerUtil.error(AiProviderComparisonComponent.class, "Failed to load AI response HTML template.", e);
            }
        });
    }

	private void getProviderProperties() {
		try (TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
		    TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);

		    TSelect<TPropertie> select = new TSelect<>(TPropertie.class);
		    select.addAndCondition("key", TCompareOp.LIKE, "sys.grok");
		    select.addOrCondition("key", TCompareOp.LIKE, "sys.openai");
		    select.addOrCondition("key", TCompareOp.LIKE, "sys.gemini");

		    TResult<List<TPropertie>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), select);

		    if (res.getState().equals(TState.SUCCESS) && res.getValue() != null && !res.getValue().isEmpty()) {
		        openaiKey = getValue(res.getValue(), TSystemPropertie.OPENAI_KEY.getValue());
		        openaiModel = getValue(res.getValue(), TSystemPropertie.OPENAI_MODEL.getValue());

		        grokKey = getValue(res.getValue(), TSystemPropertie.GROK_KEY.getValue());
		        grokModel = getValue(res.getValue(), TSystemPropertie.GROK_MODEL.getValue());

		        geminiKey = getValue(res.getValue(), TSystemPropertie.GEMINI_KEY.getValue());
		        geminiModel = getValue(res.getValue(), TSystemPropertie.GEMINI_MODEL.getValue());                        
		    }
		    
		} catch (Exception e) {
		    TLoggerUtil.error(AiProviderComparisonComponent.class, e.toString(), e);
		}
	}

    private void initializeUI() {
        // Input Area
        TLabel promptLabel = new TLabel("Prompt:");
        promptArea = new TextArea();
        promptArea.setPromptText("Enter your prompt for all providers...");
        promptArea.setPrefRowCount(4);
        VBox.setVgrow(promptArea, Priority.ALWAYS); // Allow prompt to grow slightly but mostly content below

        // Controls
        sendButton = new TButton("Send");
        sendButton.setOnAction(e -> sendRequests());

        clearButton = new TButton("Clear");
        clearButton.setOnAction(e -> promptArea.clear());

        TToolBar controls = new TToolBar(sendButton, clearButton);

        VBox inputBox = new VBox(5, promptLabel, promptArea, controls);

        // Output Area (WebViews)
        openaiWebView = new WebView();
        grokWebView = new WebView();
        geminiWebView = new WebView();

        SplitPane splitPane = new SplitPane();
        splitPane.setStyle("-fx-background-color: transparent;");
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.getItems().addAll(wrapWebView(openaiWebView, "OpenAI"), wrapWebView(grokWebView, "Grok"),
                wrapWebView(geminiWebView, "Gemini"));
        splitPane.setDividerPositions(0.33, 0.66);
        splitPane.setMaxHeight(400);

        VBox.setVgrow(splitPane, Priority.ALWAYS);

        this.getChildren().addAll(inputBox, splitPane);
    }

    private StackPane wrapWebView(WebView wv, String title) {
        TLabel label = new TLabel(title);
        //VBox box = new VBox(5, label, wv);
        //VBox.setVgrow(wv, Priority.ALWAYS);
        //VBox.setMargin(box, new Insets(0, 10, 0, 10));
        
        TFieldBox fieldBox = new TFieldBox(title, label, wv, TLabelPosition.TOP);
        fieldBox.setId("t-fieldbox-info");
        
        StackPane stack = new StackPane(fieldBox);
        
        if(title.equals("Grok")) {
        	grokProgressIndicator = new TProgressIndicator(stack);
        } else if(title.equals("Gemini")) {
			geminiProgressIndicator = new TProgressIndicator(stack);
		} else if(title.equals("OpenAI")) {
			openaiProgressIndicator = new TProgressIndicator(stack);
		}
        
        return stack;
    }
    
    private void showAlert(String content) {
    	view.tShowModal(new TMessageBox(content), true); 
    }

    private void sendRequests() {
        String prompt = promptArea.getText();
        if (prompt == null || prompt.trim().isEmpty()) {
            return;
        }

        setLoadingState(true);        
        executeComparison(prompt);        
    }

    private void setLoadingState(boolean loading) {
        sendButton.setDisable(loading);
    }

    private void executeComparison(String prompt) {    	
        // Run parallel requests
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(() -> {
        	openaiService.prompt = prompt;
        	openaiService.systemPrompt = SYSTEM_PROMPT;
        	openaiService.startProcess();
        });
        executor.submit(() -> {
        	grokService.prompt = prompt;
        	grokService.systemPrompt = SYSTEM_PROMPT;
        	grokService.startProcess();
        });
        executor.submit(() -> {
        	geminiService.prompt = prompt;
        	geminiService.systemPrompt = SYSTEM_PROMPT;
        	geminiService.startProcess();
        });

        executor.shutdown();
    }

    private String getValue(List<TPropertie> props, String key) {
        return props.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .map(TPropertie::getValue)
                .orElse(null);
    }
    
    private void updateWebViewContent(TerosWebViewBridge bridge, String content) {
        if (bridge != null) {
            Platform.runLater(() -> bridge.run(content));
        }
    }
    
    private static class TerosService extends TProcess<String> {
    	
    	
		private String prompt;
		private String systemPrompt;
		private IAiTerosService iaServ;
		
		@SuppressWarnings("rawtypes")
		public void buildIaTerosService(String key, String model, String sysPrompt, 
				AiServiceProvider provider) {
			try {
	            // Assuming AiTerosServiceFactory and IAiTerosService usage
				iaServ = AiTerosServiceFactory.newInstance(key, model, sysPrompt, provider);
	            TFunction[] arr = new TFunction[] {
						TFunctionHelper.listAllViewPathFunction(),
						TFunctionHelper.getViewInfoFunction(),
						TFunctionHelper.callUpViewFunction(),
						TFunctionHelper.getCreateFileFunction()};
	            
	            arr = ArrayUtils.addAll(arr, TFunctionHelper.getAppsFunction());
	            iaServ.createFunctionExecutor(arr);
	            
	            
	        } catch (Exception e) {
	            TLoggerUtil.error(AiProviderComparisonComponent.class, "Error calling " + provider, e);
	        }
		}

		@Override
		protected TTaskImpl<String> createTask() {
			return new TTaskImpl<String>() {
				@Override
				protected String call() throws Exception {
					return iaServ.call(prompt, systemPrompt);
				}

				@Override
				public String getServiceNameInfo() {
					return null;
				}
			};
		}
    }
}
