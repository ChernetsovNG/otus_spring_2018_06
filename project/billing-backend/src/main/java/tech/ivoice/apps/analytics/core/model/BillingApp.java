package tech.ivoice.apps.analytics.core.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "billingApp")
public class BillingApp {

    @Id
    private String id;

    /**
     * id приложения
     */
    private String appUuid;

    /**
     * название приложения
     */
    private String appName;

    /**
     * url для выгрузки spent resources для данного приложения
     */
    private String spentResourcesUrl;
}
