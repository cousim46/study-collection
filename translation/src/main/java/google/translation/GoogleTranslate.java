package google.translation;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.TranslateOptions.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleTranslate {
	@Value("${google-translate.key}")
	private String key;

	public String translateV2(TranslateRequest request) {

		Translate translate = TranslateOptions.getDefaultInstance().getService();

		return translate.translate(
			"번역하고 싶은 말을 번역해보세요", getTextTranslateOption(request.getFormat(), request.getSource(), request.getTarget())
		).getTranslatedText();
	}

	private TranslateOption[] getTextTranslateOption(String format,String sourceLanguage, String targetLanguage) {
		return new TranslateOption[] {
			TranslateOption.format(format),TranslateOption.sourceLanguage(sourceLanguage), TranslateOption.targetLanguage(targetLanguage)
		};
	}

}
