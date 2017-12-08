package com.foundation;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.learnium.RNDeviceInfo.RNDeviceInfo;

import java.util.ArrayList;
import java.util.List;

class ManuallyConfiguredException extends Exception {
  ManuallyConfiguredException(String msg) {
    super(msg);
  }
}

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {

      ArrayList<ReactPackage> packages = new ArrayList<>();
      packages.add(new MainReactPackage());
      packages.add(new RNDeviceInfo());

      // Load the packages from the native dependencies
      for (String reactPackage:BuildConfig.REACT_PACKAGES) {
        try {
          final Class<ReactPackage> LibReactPackage = (Class<ReactPackage>) this.getClass().getClassLoader().loadClass(reactPackage);

          // In case the package has already been instantiated, just ignore it
          for (ReactPackage rp : packages) {
            if (LibReactPackage.isInstance(rp)) {
              System.out.println();
              throw new ManuallyConfiguredException("ReactPackage" + reactPackage + " configured manually");
            }
          }

          // See if we have a default constructor, in which case instantiate one
          packages.add(LibReactPackage.newInstance());

        } catch (ClassNotFoundException e) {
          System.err.println("Error loading react package " + reactPackage);
          System.err.println(e);
        } catch (InstantiationException e) {
          System.err.println("The ReactPackage" + reactPackage + " needs to be configured manually. Please check the library documentation.");
        } catch (IllegalAccessException e) {
          System.err.println("The ReactPackage" + reactPackage + " needs to be configured manually. Please check the library documentation.");
        } catch (ManuallyConfiguredException err) {
          System.out.println("INFO::" + err.getMessage());
        }
      }

      return packages;
    }

    @Override
    protected String getJSMainModuleName() {
      return "index";
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
  }
}
