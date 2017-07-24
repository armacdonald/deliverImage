package deliverImage;

import java.util.List;

public interface IConfig {
	String getInputDirectory();
	List<CustomerConfig> getCustomerConfigs();
}
