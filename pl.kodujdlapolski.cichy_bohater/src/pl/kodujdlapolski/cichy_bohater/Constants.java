package pl.kodujdlapolski.cichy_bohater;

public interface Constants {
	String LOG = "pl.kodujdlapolski.cichy_bohater";

	String defaultLanguage = "pl";

	String api_server = "http://api.cichybohater.pl/";
	final String create_intervention_url = api_server + "interventions";

	String CATEGORY_ID_EXTRA = "incidentCategoryId";

	String DEFAULT_LANG = "pl";

	String SCHEMA_LIST_EXTRA = "categories_list";
	String SCHEMA_EXTRA = "category";

	String INCIDENT_DATA_EXTRA = "incident_data";

	int TAKE_PHOTO_ACTION = 1;
	int SELECT_PHOTO_ACTION = 2;

	String ERROR_MESSAGE = "error_message";

	final String TEXT_FIELD_TYPE = "text";
	final String TEXT_AREA_FIELD_TYPE = "textarea";
	final String CHECKBOX_FIELD_TYPE = "checkbox";
	final String COMBO_FIELD_TYPE = "select";
	final String NUMBER_FIELD_TYPE = "number";
	final String PHOTO_FIELD_TYPE = "photo";

}