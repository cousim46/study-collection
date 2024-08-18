package google.translation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/translate")
@RestController
@RequiredArgsConstructor
public class TranslateController {
	private final GoogleTranslate googleTranslate;

	@GetMapping("/google/v2")
	public String googleTranslateV2(TranslateRequest request) {
		return googleTranslate.translateV2(request);
	}
}
