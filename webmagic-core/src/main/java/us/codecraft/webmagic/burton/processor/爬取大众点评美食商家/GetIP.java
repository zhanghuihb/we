package us.codecraft.webmagic.burton.processor.爬取大众点评美食商家;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * 定时获取动态IP
 *
 * @author Tainy
 * @date 2018/6/5 13:25
 */
public class GetIP implements Runnable{

    private static Logger LOGGER = LoggerFactory.getLogger(GetIP.class);
    public static List<Proxy> getIp() throws IOException {
        String order = "43c77a070af3180029828c3c4588ff47";
        URL url = null;
        try {
            url = new URL("http://api.ip.data5u.com/dynamic/get.html?order=" + order + "&ttl&random=true");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(3000);
            connection = (HttpURLConnection)url.openConnection();

            InputStream raw = connection.getInputStream();
            InputStream in = new BufferedInputStream(raw);
            byte[] data = new byte[in.available()];
            int bytesRead = 0;
            int offset = 0;
            while(offset < data.length) {
                bytesRead = in.read(data, offset, data.length - offset);
                if(bytesRead == -1) {
                    break;
                }
                offset += bytesRead;
            }
            in.close();
            raw.close();
            String res = new String(data, "UTF-8");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>当前返回IP " + res);
            if(StringUtils.isNotEmpty(res)){
                res = res.replace("\n","");
                String[] proxyArr = res.split(",");
                String[] proxy = proxyArr[0].split(":");
                List<Proxy> proxyList = Arrays.asList(new Proxy(proxy[0], Integer.parseInt(proxy[1])),new Proxy(proxy[0], Integer.parseInt(proxyArr.length > 1 ? proxyArr[1] : proxy[1])));
                return proxyList;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>获取IP出错, " + e.getMessage());
        }

        return null;
    }

    @Override
    public void run() {
        try {
            getIp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpClientDownloader updateHttpClientDownloader(){
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(updateProxyProvider());
        return httpClientDownloader;
    }

    public static ProxyProvider updateProxyProvider(){
        return SimpleProxyProvider.from(updateProxy());
    }

    public static List<Proxy> updateProxy(){
        List<Proxy> proxyList = null;
        try {
            proxyList = GetIP.getIp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("proxyList {}", JSON.toJSONString(proxyList));
        return proxyList;
    }
}
