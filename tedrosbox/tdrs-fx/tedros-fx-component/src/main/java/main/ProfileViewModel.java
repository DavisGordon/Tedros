package main;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TBigIntegerField;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TDoubleField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TIntegerField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TSliderField;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.presenter.model.TEntityModelView;


//@TCrudForm(aligment=Pos.CENTER_LEFT, style="-fx-background-color: #ffffff;", width=400, formClass = null)
@TLabelDefaultSetting(node=@TNode(style="-fx-text-fill: #003333; -fx-font: \"Helvetica\"; 	-fx-font-size: 1.4em; -fx-font-weight: bold;", parse = true), control=@TControl(prefWidth=400, parse = true), position = TLabelPosition.TOP)
public class ProfileViewModel extends TEntityModelView<TCProfile> {
	
	private SimpleLongProperty id;
	
	@TLabel(text="Senha")
	@TPasswordField(required=true, maxLength=6)
	private SimpleStringProperty senha;
	
	@TLabel(text="campoInteger")
	@TIntegerField(zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleIntegerProperty campoInteger;
	
	@TLabel(text="campoBigDecimal")
	@TBigDecimalField(zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleObjectProperty<BigDecimal> campoBigDecimal;
	
	@TLabel(text="campoBigInteger")
	@TBigIntegerField(zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleObjectProperty<BigInteger> campoBigInteger;
	
	@TLabel(text="campoNumberSpin", control=@TControl(prefWidth=200, parse = true))
	@TNumberSpinnerField(maxValue = 100, zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleDoubleProperty campoNumberSpin ;
	
	@TLabel(text="campoSlider")
	@TSliderField(max = 100, min = 0, showTickMarks=true, showTickLabels=true, snapToTicks=false, zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleDoubleProperty campoSlider;
	
	@TLabel(text="Nome")
	@TTextField(textInputControl=@TTextInputControl(promptText="Digite o nome", parse = true), control=@TControl(tooltip="Digite o nome", parse = true), required=true, maxLength=5)
	private SimpleStringProperty name;
	
	/*@TLabel(text="Sobrenome", position=TLabelPosition.TOP)
	@TTextField(promptText="Digite o sobrenome", toolTip="Digite o sobrenome", notNull=true)
	private SimpleStringProperty lastName;
	
	@TLabel(text="Nome do meio", position=TLabelPosition.TOP)
	@TTextField(promptText="Digite o nome do meio", toolTip="Digite o nome do meio", notNull=true)
	private SimpleStringProperty surName;*/
	
	@TLabel(text="Endereço")
	@TTextAreaField(textInputControl=@TTextInputControl(promptText="Digite o endereço", parse = true),
	control=@TControl(tooltip="Digite o endereço", parse = true), prefColumnCount=50, prefRowCount=6, required=true)
	private SimpleStringProperty endereco;
	
	@TLabel(text="Funções avançadas")
	@TCheckBoxField(label=@TLabel(text="Ativar"), control=@TControl(tooltip="ativar tudo", parse = true), required=true)
	private SimpleBooleanProperty ativar;
	
	/*@TLabel(text="DESCRIÇÃO", position=TLabelPosition.TOP)
	@TTextArea(promptText="DESCRIÇÃO", toolTip="Digite a DESCRIÇÃO", prefColumnCount=50, prefRowCount=6, notNull=true)
	private SimpleStringProperty description;*/
	
	@TLabel(text="Quantidade", position=TLabelPosition.RIGHT)
	@TDoubleField(zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleDoubleProperty quantidade;
	
	/*@TLabel(text="Sexo", position=TLabelPosition.BOTTOM)
	@TComboBoxField(toolTip="Escolha o perfil", firstItemTex="-- Selecione", required=true)
	private SimpleObjectProperty<TCProfile> perfil;*/
	
	@TLabel(text="Escolha")
	@THorizontalRadioGroup(spacing=5, required=true, alignment= Pos.CENTER_LEFT
			,radioButtons={
			@TRadioButtonField(userData="M", text="Masculino"),
			@TRadioButtonField(userData="F", text="Feminino")
			
	})
	private SimpleStringProperty sexo;
	
	@TLabel(text="Data")
	@TDatePickerField(control=@TControl(tooltip="Escolha uma data", parse = true), required=true)
	private SimpleObjectProperty<Date> data;
	
	/*@TPickListField(required=true, sourceLabel="Perfis", selectedLabel="Perfis selecionados")
	@TLabel(text="Teste PickList")
	@TModelViewCollectionType(modelViewClass=ProfileViewModel.class, entityClass=TCProfile.class)
	private ObservableList<ProfileViewModel> profiles;*/
	
	public ProfileViewModel(){
		super(null);
	}
	
	public ProfileViewModel(TCProfile entidade) {
		super(entidade);
	}
	
	
	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		String str = "";
		try {
			
			Method[] metodos = this.getClass().getDeclaredMethods();
			for (Method method : metodos) {
				
				if(method.getName().indexOf("Profiles")!=-1 || method.getName().indexOf("Perfil")!=-1){
					continue;
				}
				
				if(method.getName().indexOf("get")!=-1){
					str += method.invoke(this)+"; ";
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);
		
		if(this.getEntity()!=null && this.getEntity().getProfiles()!=null)
			System.out.println(((getName()!=null)? getName().getValue() : "") + " "+ this.getEntity().getProfiles().size());
		return (getName()!=null)? getName().getValue() : "";
		
	}
		
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof ProfileViewModel))
			return false;
		
		ProfileViewModel p = (ProfileViewModel) obj;
		
		
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null){
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		}	
		
		if(getName()!=null && getName().getValue()!=null &&  p.getName()!=null && p.getName().getValue()!=null)
			return getName().getValue().equals(p.getName().getValue());
		
		return false;
	}
	

	public SimpleStringProperty getEndereco() {
		return endereco;
	}


	public void setEndereco(SimpleStringProperty endereco) {
		this.endereco = endereco;
	}


	public SimpleBooleanProperty getAtivar() {
		return ativar;
	}


	public void setAtivar(SimpleBooleanProperty ativar) {
		this.ativar = ativar;
	}


	public SimpleDoubleProperty getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(SimpleDoubleProperty quantidade) {
		this.quantidade = quantidade;
	}


	/*public SimpleObjectProperty<TCProfile> getPerfil() {
		return perfil;
	}


	public void setPerfil(SimpleObjectProperty<TCProfile> perfil) {
		this.perfil = perfil;
	}
*/

	public SimpleObjectProperty<Date> getData() {
		return data;
	}


	public void setData(SimpleObjectProperty<Date> data) {
		this.data = data;
	}


/*	public ObservableList<ProfileViewModel> getProfiles() {
		return profiles;
	}


	public void setProfiles(ObservableList<ProfileViewModel> profiles) {
		this.profiles = profiles;
	}*/

	public SimpleIntegerProperty getCampoInteger() {
		return campoInteger;
	}

	public void setCampoInteger(SimpleIntegerProperty campoInteger) {
		this.campoInteger = campoInteger;
	}

	public SimpleObjectProperty<BigDecimal> getCampoBigDecimal() {
		return campoBigDecimal;
	}

	public void setCampoBigDecimal(SimpleObjectProperty<BigDecimal> campoBigDecimal) {
		this.campoBigDecimal = campoBigDecimal;
	}

	public SimpleObjectProperty<BigInteger> getCampoBigInteger() {
		return campoBigInteger;
	}

	public void setCampoBigInteger(SimpleObjectProperty<BigInteger> campoBigInteger) {
		this.campoBigInteger = campoBigInteger;
	}

	
	public SimpleStringProperty getSexo() {
		return sexo;
	}

	public void setSexo(SimpleStringProperty sexo) {
		this.sexo = sexo;
	}

	public SimpleStringProperty getSenha() {
		return senha;
	}

	public void setSenha(SimpleStringProperty senha) {
		this.senha = senha;
	}

	public SimpleDoubleProperty getCampoNumberSpin() {
		return campoNumberSpin;
	}

	public void setCampoNumberSpin(SimpleDoubleProperty campoNumberSpin) {
		this.campoNumberSpin = campoNumberSpin;
	}

	public SimpleDoubleProperty getCampoSlider() {
		return campoSlider;
	}

	public void setCampoSlider(SimpleDoubleProperty campoSlider) {
		this.campoSlider = campoSlider;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	public final SimpleLongProperty getId() {
		return id;
	}

	public final void setId(SimpleLongProperty id) {
		this.id = id;
	}
	
	

}
