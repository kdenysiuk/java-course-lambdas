package com.javakeith.carina.demo.gui.pages.desktop;

import com.javakeith.carina.demo.gui.components.compare.*;
import com.javakeith.carina.demo.gui.pages.common.CompareModelsPageBase;
import com.zebrunner.carina.utils.Configuration;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;
import java.util.function.*;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CompareModelsPageBase.class)
public class CompareModelsPage extends CompareModelsPageBase {

    @FindBy(xpath = "//div[contains(@class, 'candidate-search')]")
    private List<CandidateBlock> candidateBlocks;

    @FindBy(className = "compare-candidates")
    private ExtendedWebElement compareMenu;

    public CompareModelsPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(compareMenu);
    }

    @Override
    public List<ModelSpecs> compareModels(String... models) {
        CandidateBlock candidateBlock;
        List<ModelSpecs> modelSpecs = new ArrayList<>();
        waitUntil(ExpectedConditions.presenceOfElementLocated(compareMenu.getBy()), (Configuration.getLong(Configuration.Parameter.EXPLICIT_TIMEOUT)));
        ModelSpecs modelSpec;

        for (int index = 0; index < models.length; index++) {
            modelSpec = new ModelSpecs();
            candidateBlock = candidateBlocks.get(index);
            candidateBlock.sendKeysToInputField(models[index]);
            candidateBlock.getFirstPhone();
            for (ModelSpecs.SpecType type : ModelSpecs.SpecType.values()) {
                String xpath = getXpath(type.getType(), index, (k, v) -> String.format("//tr[.//a[text()='%s']]//td[@class='nfo'][%d]", k, v + 1));
                ExtendedWebElement spec = getElement(xpath, k -> findExtendedWebElement(By.xpath(k)));
                modelSpec.setToModelSpecsMap(type, spec.getText());
            }
            modelSpecs.add(modelSpec);
        }
        return modelSpecs;
    }

    private static String getXpath(String type, int index, BiFunction<String, Integer, String> lambda) {
        return lambda.apply(type, index);
    }

    public static ExtendedWebElement getElement(String xpath, Function<String, ExtendedWebElement> lambda) {
        return lambda.apply(xpath);
    }
}
