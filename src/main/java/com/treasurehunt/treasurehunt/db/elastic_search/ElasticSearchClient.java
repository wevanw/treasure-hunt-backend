package com.treasurehunt.treasurehunt.db.elastic_search;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ElasticSearchClient {
    private static final String ELASTIC_SEARCH_ENDPOINT = "34.70.120.75";
    private final RestHighLevelClient client;

    public ElasticSearchClient() throws IOException {
        // Get username and password from properties
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream =
                ElasticSearchClient.class.getClassLoader().getResourceAsStream(propFileName);
        prop.load(inputStream);

        String es_user = prop.getProperty("sql_user");
        String es_password = prop.getProperty("sql_password");

        // Setup login credentials
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(es_user, es_password));

        // Setup ElasticSearch Low Level REST client
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(ELASTIC_SEARCH_ENDPOINT, 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });

        // Setup ElasticSearch High Level REST client
        client = new RestHighLevelClient(builder);

    }
}