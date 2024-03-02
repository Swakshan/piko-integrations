package app.revanced.integrations.twitter.settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.twitter.ui.widget.LegacyTwitterPreferenceCategory;

import app.revanced.integrations.shared.Utils;
import app.revanced.integrations.shared.settings.StringSetting;

@SuppressWarnings("deprecation")
public class SettingsActivity extends Activity {
    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(Utils.getResourceIdentifier("preference_fragment_activity", "layout"));
        Toolbar toolbar = findViewById(Utils.getResourceIdentifier("toolbar", "id"));
        toolbar.setNavigationIcon(Utils.getResourceIdentifier("ic_vector_arrow_left", "drawable"));
        toolbar.setTitle("Mod Settings");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        getFragmentManager().beginTransaction().add(Utils.getResourceIdentifier("fragment_container", "id"), new Screen()).commit();
    }

    public static class Screen extends PreferenceFragment {
        private Context context;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            context = getContext();

            PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

            LegacyTwitterPreferenceCategory downloadPrefs = preferenceCategory("Download", screen);
            downloadPrefs.addPreference(listPreference(
                    "Public Folder",
                    "The public folder to use for video downloads",
                    Settings.VID_PUBLIC_FOLDER
            ));
            downloadPrefs.addPreference(editTextPreference(
                    "Download Subfolder",
                    "The subfolder to download videos to ([PublicFolder]/[Subfolder])",
                    Settings.VID_SUBFOLDER
            ));

            LegacyTwitterPreferenceCategory customHostPrefs = preferenceCategory("Share link", screen);
            customHostPrefs.addPreference(editTextPreference(
                    "Host",
                    "The custom host name for when sharing",
                    Settings.SHARING_HOST
            ));

            setPreferenceScreen(screen);
        }

        private Preference editTextPreference(String title, String summary, StringSetting setting) {
            EditTextPreference preference = new EditTextPreference(context);
            preference.setTitle(title);
            preference.setDialogTitle(title);
            preference.setSummary(summary);
            preference.setKey(setting.key);
            preference.setDefaultValue(setting.defaultValue);

            return preference;
        }

        private Preference listPreference(String title, String summary, StringSetting setting) {
            ListPreference preference= new ListPreference(context);
            preference.setTitle(title);
            preference.setDialogTitle(title);
            preference.setSummary(summary);
            preference.setKey(setting.key);
            preference.setDefaultValue(setting.defaultValue);
            preference.setEntries(new CharSequence[]{"Movies", "DCIM", "Pictures", "Download"});
            preference.setEntryValues(new CharSequence[]{"Movies", "DCIM", "Pictures", "Download"});

            return preference;
        }

        private LegacyTwitterPreferenceCategory preferenceCategory(String title, PreferenceScreen screen) {
            LegacyTwitterPreferenceCategory preferenceCategory = new LegacyTwitterPreferenceCategory(context);
            preferenceCategory.setTitle(title);
            screen.addPreference(preferenceCategory);
            return preferenceCategory;
        }
    }
}