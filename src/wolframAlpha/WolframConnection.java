package wolframAlpha;

import com.wolfram.alpha.*;

/**
 * Created by Lucroth on 2016-11-07.
 */
public final class WolframConnection {
    public static String queryWolfram(String input) {
        WAEngine engine = new WAEngine();
        engine.setAppID("KJ99UR-5RKVVJE2V5");
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(input);

        try {
            WAQueryResult queryResult = engine.performQuery(query);

            if (queryResult.isError()) {
                return "Error message: " + queryResult.getErrorMessage();
            } else if (!queryResult.isSuccess()) {
                return "Query was not understood; no results available.";
            } else {
                for (WAPod pod : queryResult.getPods()) {
                    if (!pod.isError()) {
                        for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {
                                    if (pod.getTitle().equals("Indefinite integral") || pod.getTitle().equals("Result") || pod.getTitle().equals("Solution")) {
                                        return ((WAPlainText) element).getText();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (WAException e) {
            e.printStackTrace();
        }
        return "fail";
    }
}
