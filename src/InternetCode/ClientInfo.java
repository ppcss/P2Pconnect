package InternetCode;

import java.io.Serializable;

public class ClientInfo implements Serializable {
	
	//�ͻ���id
    private String clientId;

    //�ͻ���ip
    private String ip;
    
    //�ͻ��˶˿ں�
    private String port;

    //�Ƿ��Ծ
    private String isActive;

    //�Ƿ��ѯ
    private String isQuery;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String isActive() {
        return isActive;
    }

    public void setActive(String active) {
        isActive = active;
    }

    public String isQuery() {
        return isQuery;
    }

    public void setQuery(String query) {
        isQuery = query;
    }
    @Override
    public String toString() {
        return "ClientInfo{" +"clientId='" + clientId + '\'' +
                ", ip='" + ip + '\'' + ", port='" + port + '\'' +
                ", isActive='" + isActive + '\'' + ", isQuery='" + isQuery + '\'' +
                '}';
    }
}

