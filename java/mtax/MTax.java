import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MTax implements Constant {
    
    public MTax(){
        
    }
    
public static List<String> validateTax(List<XTax> xTaxList) {
        
        List<String> errorList = new ArrayList<>();
        
        if(xTaxList != null && xTaxList.size() > 0) {
            List<String> validIds = new ArrayList<>();
            boolean flagAllLocalTax = true;
			
            for (XTax tax : xTaxList) {
                
				if(tax.getId() != null){
                    validIds.add(tax.getId().toString());
                }

                if(tax.getTax() == null) {
                    errorList.add("El impuesto es obligatorio, id: " + tax.getId());
                }
                
                if(!tax.isLocal()){
                	flagAllLocalTax = false;
                }
            }
            
			if(flagAllLocalTax){
                errorList.add("Debe de incluir al menos una tasa no local");
            }
            
			if(validIds.size() > 0 && flagAllLocalTax == false){
				
				errorList = setTaxsValidated(validIds, errorList, xTaxList);         

			}
                
        }
        
        return errorList;
        
    }
        
        
    protected static List<String> setTaxsValidated(List<String> validIds, List<String> errorList, List<XTax> xTaxList) {
    	
		List<XTax> xTaxsValidated = TaxsByListId(validIds, false);
		
		if (xTaxsValidated.size() != validIds.size()) {
			errorList.add("Existen datos no guardados previamente");
		} else {
			HashMap<String, Date> mapTaxs = new HashMap<>();

			for (XTax tax : xTaxsValidated) {
				mapTaxs.put(tax.getId().toString(), tax.getCreated());
			}

			for (int i = 0; i < xTaxList.size(); i++) {
				if (xTaxList.get(i).getId() != null) {
					xTaxList.get(i).setCreated(mapTaxs.get(xTaxList.get(i).getId().toString()));
				}
			}
		}
		
		return errorList;
	}
    
}
