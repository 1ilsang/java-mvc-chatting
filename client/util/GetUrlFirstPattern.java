package util;

import dto.CommandDTO;

/**
 * Separate URL for Controllers
 * Like Math Class - static util method
 */
public class GetUrlFirstPattern {
    private GetUrlFirstPattern(){}
    private static GetUrlFirstPattern getUrlFirstPattern = new GetUrlFirstPattern();
    private static GetUrlFirstPattern getInstance() {
        return getUrlFirstPattern;
    }
    public static String getStringPattern(CommandDTO commandDTO) {
        int endIdx = commandDTO.getUrl().indexOf("/", 1);
        if(endIdx == -1) endIdx = commandDTO.getUrl().length();
        String pattern = commandDTO.getUrl().substring(0, endIdx);
        commandDTO.setUrl(commandDTO.getUrl().substring(endIdx));
        return pattern;
    }
}
