package osotnikov.web.json;
 
public class JsonResponse{
 
    private String status;
    private String errorMsg;
    private Object data;
 
    public JsonResponse() {
    }
     
    public JsonResponse(String status) {
        this.status = status;
    }    
         
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
     
    public String getErrorMsg() {
        return errorMsg;
    }
 
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
     
    public Object getData() {
        return data;
    }
 
    public void setData(Object data) {
        this.data = data;
    }
     
}