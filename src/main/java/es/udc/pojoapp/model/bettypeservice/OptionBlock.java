package es.udc.pojoapp.model.bettypeservice;

import java.util.List;

import es.udc.pojoapp.model.option.Option;

public class OptionBlock {
	
	    private List<Option> options;
	    private boolean existMoreOptions;

	    public OptionBlock(List<Option> options, boolean existMoreOptions) {
	        
	        this.options = options;
	        this.existMoreOptions = existMoreOptions;

	    }
	    
	    public List<Option> getOptions() {
	        return options;
	    }
	    
	    public boolean getexistMoreOptions() {
	        return existMoreOptions;
	    }
}
