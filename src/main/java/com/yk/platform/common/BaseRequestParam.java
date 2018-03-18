package com.yk.platform.common;

public class BaseRequestParam
{

    private String appId; // 发起访问请求的应用id
    private String token; // 用户证书
    private String timestamp; // 时间戳
    private String machineId; // 机器码
    private String mac; // mac地址

    private String accountId; // 访问账户id
    private String deviceId; // 设备唯一识别号（用于做为推送主题）
    private String program; // 访问程序类型

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getMachineId()
    {
        return machineId;
    }

    public void setMachineId(String machineId)
    {
        this.machineId = machineId;
    }

    public String getMac()
    {
        return mac;
    }

    public void setMac(String mac)
    {
        this.mac = mac;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getProgram()
    {
        return program;
    }

    public void setProgram(String program)
    {
        this.program = program;
    }

    @Override
    public String toString()
    {
        return "BaseRequestParam [appId=" + appId + ", token=" + token + ", timestamp=" + timestamp + ", machineId=" + machineId + ", mac=" + mac
                + ", accountId=" + accountId + ", deviceId=" + deviceId + ", program=" + program + "]";
    }
}
