package com.javakeith.carina.demo.gui.pages.desktop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.javakeith.carina.demo.gui.pages.common.ModelInfoPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;

import java.util.function.Function;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ModelInfoPageBase.class)
public class ModelInfoPage extends ModelInfoPageBase {

    @FindBy(css = ".help-display strong")
    private ExtendedWebElement displayInfoLabel;

    @FindBy(css = ".help-camera strong")
    private ExtendedWebElement cameraInfoLabel;

    @FindBy(css = ".help-expansion strong")
    private ExtendedWebElement displayRamLabel;

    @FindBy(css = ".help-battery strong")
    private ExtendedWebElement batteryInfoLabel;

    public ModelInfoPage(WebDriver driver) {
        super(driver);
    }

    public String readDisplay() {
        assertElementPresent(displayInfoLabel);
        return displayInfoLabel.getText();
    }

    public String readCamera() {
        assertElementPresent(cameraInfoLabel);
        return getTextFromElementUppercase(cameraInfoLabel, k -> k.getText().toUpperCase());
    }

    private static String getTextFromElementUppercase(ExtendedWebElement element, Function<ExtendedWebElement, String> lambda) {
        return lambda.apply(element);
    }

    public String readRam() {
        assertElementPresent(displayRamLabel);
        return displayRamLabel.getText();
    }

    public String readBattery() {
        assertElementPresent(displayInfoLabel);
        return batteryInfoLabel.getText();
    }

}
