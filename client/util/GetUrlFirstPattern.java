package util;

import dto.ModelAndView;

/**
 * Separate URL for Controllers - static util method
 */
public class GetUrlFirstPattern {
    private GetUrlFirstPattern(){}

    public static String getStringPattern(ModelAndView modelAndView) {
        if(modelAndView == null) return null;
        int endIdx = modelAndView.getUrl().indexOf("/", 1);

        if(endIdx == -1) endIdx = modelAndView.getUrl().length();
        String pattern = modelAndView.getUrl().substring(0, endIdx);
        modelAndView.setUrl(modelAndView.getUrl().substring(endIdx));

        return pattern;
    }
}
