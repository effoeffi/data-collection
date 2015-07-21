package com.hp.gaia.agent.collection;

import com.hp.gaia.agent.config.ProtectedValue;
import com.hp.gaia.agent.config.Proxy;
import com.hp.gaia.agent.service.ProtectedValueDecrypter;
import com.hp.gaia.provider.ProxyProvider;
import org.apache.commons.lang.StringUtils;

import java.net.InetSocketAddress;
import java.net.Proxy.Type;
import java.net.URL;

public class ProxyProviderImpl implements ProxyProvider {

    private final ProtectedValueDecrypter protectedValueDecrypter;

    private final Proxy proxy;

    public ProxyProviderImpl(final ProtectedValueDecrypter protectedValueDecrypter, final Proxy proxy) {
        this.protectedValueDecrypter = protectedValueDecrypter;
        this.proxy = proxy;
    }

    @Override
    public java.net.Proxy getProxy() {
        if (proxy != null && !StringUtils.isEmpty(proxy.getHttpProxy())) {
            URL proxyUrl = proxy.getHttpProxyURL();
            return new java.net.Proxy(Type.HTTP, InetSocketAddress.createUnresolved(proxyUrl.getHost(), proxyUrl.getPort()));
        } else {
            return java.net.Proxy.NO_PROXY;
        }
    }

    @Override
    public String getProxyUsername() {
        if (proxy != null) {
            return proxy.getHttpProxyUser();
        }
        return null;
    }

    @Override
    public String getProxyPassword() {
        if (proxy != null) {
            if (proxy.getHttpProxyPassword() != null) {
                ProtectedValue protectedValue = proxy.getHttpProxyPassword();
                if (protectedValue.getType() == ProtectedValue.Type.ENCRYPTED) {
                    return protectedValueDecrypter.decrypt(protectedValue);
                } else {
                    return protectedValue.getValue();
                }
            }
        }
        return null;
    }
}
