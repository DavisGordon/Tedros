/**
 * 
 */
package com.tedros.fxapi.control.tablecell;

import com.tedros.fxapi.control.TNumberSpinnerField;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
public class TNumberSpinnerFieldTableCell<M> extends TableCell<M, Number> {
		
		private HBox box;
		private Button btn;
        private TNumberSpinnerField textField;
 
        public TNumberSpinnerFieldTableCell() {
        }
 
        @Override
        public void startEdit() {
        	
        	 if (! isEditable()
                     || ! getTableView().isEditable()
                     || ! getTableColumn().isEditable()) {
                 return;
             }
             super.startEdit();

             if (isEditing()) {
                 if (textField == null) {
                	 createTextField();
                 }
                 
                 if (textField != null) {
                     textField.setValue(getItem());
                 }
                 setText(null);

                 if (getGraphic() == null) {
                     setGraphic(box);
                 }

                 textField.selectAll();

             }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText(getItem()!=null ? getItem().toString() : "");
            setGraphic(null);
        }
 
        @Override
        public void updateItem(Number item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(box);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
 
        private void createTextField() {
        	
        	btn = new Button("OK");
        	btn.setMinWidth(50);
            btn.setOnAction(e ->{
            	commitEdit(textField.getValue());
            	e.consume();
            });
        	textField = new TNumberSpinnerField();
            box = new HBox();
            box.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            box.getChildren().addAll(textField, btn);
            HBox.setHgrow(btn, Priority.ALWAYS);
            HBox.setHgrow(textField, Priority.SOMETIMES);
            box.setAlignment(Pos.CENTER_LEFT);
            
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
