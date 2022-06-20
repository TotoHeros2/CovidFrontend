package ch.hug.simed.covid.frontend.client.view.dyneditor;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import ch.hug.simed.covid.frontend.client.bean.ItemString;
import ch.hug.simed.covid.frontend.client.bean.RuleOp;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent;
import ch.hug.simed.covid.frontend.client.event.DataReceivedHandler;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent.TypeReceived;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import ch.hug.simed.covid.frontend.client.service.Reponse;
import gwt.material.design.addins.client.autocomplete.MaterialAutoComplete;
import gwt.material.design.addins.client.autocomplete.constants.AutocompleteType;
import gwt.material.design.addins.client.circularprogress.MaterialCircularProgress;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.addins.client.combobox.events.SelectItemEvent;
import gwt.material.design.addins.client.combobox.events.SelectItemEvent.SelectComboHandler;
import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.client.constants.ProgressType;
import gwt.material.design.incubator.client.toggle.GroupToggleButton;

public class ValueSetEditor extends Composite implements TakesValue<ItemString> {

	private static ValueSetEditorUiBinder uiBinder = GWT.create(ValueSetEditorUiBinder.class);

	interface ValueSetEditorUiBinder extends UiBinder<Widget, ValueSetEditor> {
	}
	@UiField
	MaterialEmptyState progress;
	
//	MaterialCircularProgress progress;

	
//	@UiField
//	MaterialAutoComplete autoComplete;
	
	@UiField
	MaterialComboBox<String> valueset;
	
	private List<Reponse> responses;
	
//	private String selected;

	private ItemString item;
	
	@UiField
	GroupToggleButton<String> varcharOps;


	public ValueSetEditor(ItemString item) {
		this(item,null);
	}

	public ValueSetEditor(ItemString item, String name) {
		this.item = item;
		initWidget(uiBinder.createAndBindUi(this));
		valueset.setLimit(20);
		varcharOps.addItem(RuleOp.EQ.getDisplayValue(),RuleOp.EQ.getCode());
		varcharOps.addItem(RuleOp.NEQ.getDisplayValue(),RuleOp.NEQ.getCode());
		varcharOps.setActive(0);
		item.setOp(RuleOp.EQ.getCode());
		// 30.03.21 start valueset from here / now in ItemEditor for all
		// so that service can get the whole container
//		CovidService.get().sentValueSet(item);
		AppEventBus.EVENT_BUS.addHandler(DataReceivedEvent.TYPE, new DataReceivedHandler() {


			@Override
			public void onDataReceived(DataReceivedEvent event) {
				// am I concerned ?
				if (!item.getId().equals(event.getItemId()))
				{
					return;
				}
				if (event.getTypeReceived() == TypeReceived.VALUESET)
				{
					responses = CovidService.get().getResponses();

					List<String> choices = new ArrayList<String>();
					for (int i = 0; i < responses.size() -1; i++)// the last is count
					{
						Reponse responce = responses.get(i); 
						choices.add(getValueFromResponse(responce,name));
					}
					valueset.addItems(choices);
					item.setValue(choices.get(0));
					progress.setVisible(false);
					valueset.setVisible(true);
				}
			}
		});
		
		valueset.addSelectionHandler(new SelectComboHandler<String>() {
			
			@Override
			public void onSelectItem(SelectItemEvent<String> event) {
				item.setValue(event.getSelectedValues().get(0));
			}
		});
		
		// will have use directly the valueset service instead of from the ItemEditor with field
//		CovidService.get().sentValueSet(item);
	}

	@Override
	public ItemString getValue() {
		// TODO Auto-generated method stub
//		item.setValue(null);
		return item;
	}

	@Override
	public void setValue(ItemString value) {
//		this.item = value;		
		if (RuleOp.EQ.getCode().equalsIgnoreCase(item.getOp()))
		{
			varcharOps.setActive(0);			
		}
		else
		{
			varcharOps.setActive(1);
		}
	}

	private String getValueFromResponse(Reponse response, String name)
	{
		if (name == null)
		{
			return response.getNom();
		}
		switch (name) {
		case "sexe":
			return response.getSexe();
		case "valeur_car":
			return response.getValeur_car();
		case "unite":
			return response.getUnite();
		case "utilisateur":
			return response.getUtilisateur();
		default:
			break;
		}
		return response.getNom();
		
	}
	
	@UiHandler("varcharOps")
	void selection(SelectionEvent<String> e) {
		RuleOp op = RuleOp.fromCode(e.getSelectedItem());
		item.setOp(op.getCode());

	}
	@Override
	protected void onAttach() {
		super.onAttach();
		progress.setLoading(true);
		CovidService.get().sentValueSet(item);

	}

}
