import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MTax implements Constant {
    
    public MTax(){}
    
    public static List<String> validateTax(List<XTax> xTaxList) {
        
        List<String> errorList = new ArrayList<>();
        
	if(xTaxList != null && xTaxList.size() > 0) {
            List<String> validIds = new ArrayList<>();
            boolean flagAllLocalTax = true;
			
            for (XTax tax : xTaxList) {
                
		if(tax.getId() != null){
                    validIds.add(tax.getId().toString());
                }else {
                    errorList.add("El id no puede ser nulo"):
                }

                if(tax.getTax() == null) {
                    errorList.add("El impuesto es obligatorio");
                }
                
                if(!tax.isLocal()){
               	    flagAllLocalTax = false;
                }
            }
            
	    if(flagAllLocalTax){
                errorList.add("Debe de incluir al menos una tasa no local");
            }

	    if(validIds.size() > 0 && flagAllLocalTax == false){
	        setTaxsValidated(validIds, errorList, xTaxList);
	    }
                
        }
        
        return errorList;
        
    }
        
        
    protected static void setTaxsValidated(List<String> validIds, List<XTax> xTaxList) {
    	
        List<XTax> xTaxsValidated = TaxsByListId(validIds, false);
		
        HashMap<String, Date> mapTaxs = new HashMap<>();

	for (XTax tax : xTaxsValidated) {
	    mapTaxs.put(tax.getId().toString(), tax.getCreated());
	}

        for (XTax tax : xTaxList) {
	    xTaxList.setCreated(mapTaxs.get(xTaxList.getId().toString()));
	}			
    }					   
}
