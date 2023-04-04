package literatureStats;

import java.util.*;

/**
 * An InformationDocument combines a {@link FrequencyDocument} or descendant
 * with functionality that lets users of this class gather information about that
 * document so that they can do interesting stuff with that information.
 * <p>
 * This class has a generic parameter so that a FrequencyDocument or descendant
 * can be created using just this class.
 *
 * @param <T>
 * @see DataScientist for examples.
 */
public class InformationDocument<T extends FrequencyDocument> {
    protected final T doc;

    public InformationDocument(Class<T> cls,
                               String filename) throws InstantiationException, IllegalAccessException {
        /* Although IntelliJ might complain about parts of the next lines being
           redundant, leave them because this is the way that the Java developers
           specify performing this operation.
         */
        T d = cls.newInstance();
        this.doc = d;

        this.doc.initialise(filename);
        this.doc.readDocument();
    }

    /**
     * TODO: return a short-list of the first N most frequent words (or last N if reversed)
     *  Only return the normalised word forms and nothing else.
     *
     * @param n
     * @param so
     * @return
     */
    public List<String> getTopNWords(int n, SortingOrder so) {
        List<String> topWords = new ArrayList<>();
        List<FrequencyWord> frequencyWords = getTopNFrequencyWords(n, so);

        for (FrequencyWord frequencyWord : frequencyWords) {
            topWords.add(frequencyWord.getNormalised());
        }

        return(topWords);
    }

    /**
     * TODO: return a short-list of the first N most frequent words (or last N if reversed)
     *  with each prefixed by its frequency, all formatted as a String using
     *  the default word statistics pattern.
     *
     * @param n
     * @param so
     * @return
     */
    public List<String> getTopNWordsEnumerated(int n, SortingOrder so) {
        List<String> topWords = new ArrayList<>();
        List<FrequencyWord> frequencyWords = getTopNFrequencyWords(n, so);

        for (FrequencyWord frequencyWord : frequencyWords) {
            topWords.add(frequencyWord.toString(FrequencyWord.DEFAULT_WORD_STATS_PATTERN));
        }

        return(topWords);
    }

    /**
     * TODO: return a short-list of the first N most frequent words (or last N if reversed).
     *  Returns the entire {@link FrequencyWord} object for each.
     *
     * @param n
     * @param so
     * @return
     */
    public List<FrequencyWord> getTopNFrequencyWords(int n, SortingOrder so) {
        List<FrequencyWord> topWords = new ArrayList<>();
        Map<String, Integer> unsortedWordFreqMap = new HashMap<>();
        FrequencyWord[] frequencyWords = (doc.words.values()).toArray(new FrequencyWord[0]);

        for (FrequencyWord frequencyWord : frequencyWords) {
            unsortedWordFreqMap.put(frequencyWord.getNormalised(), frequencyWord.getCount());
        }

        String[] sortedWords = sortByValue(unsortedWordFreqMap, so).keySet().toArray(new String[0]);

        for (int i = 0; i < n; i++) {
            for (FrequencyWord frequencyWord : frequencyWords) {
                if(sortedWords[i].equals(frequencyWord.getNormalised())){
                    topWords.add(frequencyWord);
                    break;
                }
            }
        }

        return topWords;
    }

    /**
     * Sort the document word list by word frequency. DO NOT CHANGE THIS.
     * This method is written in a way that supports generics and could be placed in a utility class.
     * Adapted from <a href="https://stackoverflow.com/a/2581754">this answer</a>
     * and <a href="https://stackoverflow.com/a/42535164">this answer</a> on
     * StackOverflow.
     *
     * @param map   the dictionary to be sorted
     * @param order whether to sort in ascending or descending order
     * @param <K>   the key is a String of a normalised version of the word
     * @param <V>   the value is an Integer of the word's frequency
     * @return an appropriately sorted version of the word list
     */
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
            Map<K, V> map, SortingOrder order) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        if ( order.isReversed() ) {
            list.sort(Map.Entry.<K, V>comparingByValue().reversed());
        } else {
            list.sort(Map.Entry.comparingByValue());
        }

        Map<K, V> result = new LinkedHashMap<>();
        for ( Map.Entry<K, V> entry : list ) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }


}
