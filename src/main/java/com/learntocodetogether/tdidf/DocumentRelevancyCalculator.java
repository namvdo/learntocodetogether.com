package com.learntocodetogether.tdidf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author namvdo
 */

class RelevancyScoreUtils {
    public static double getTermFrequencyScore(String term, Document document) {
         String[] words = document.doc().split("\\s+");
         int size = words.length;
         int freq = 0;
         for(final String word : words) {
             if (term.equals(word)) {
                freq++;
             }
         }
         return freq / (double) size;
    }

    public static double getInverseDocumentFrequencyScore(String term, List<Document> documents) {
        int size = documents.size();
        int freq = 0;
        for(final Document document : documents) {
            String[] words = document.doc().split("\\s+");
            for(final String word : words) {
                if (word.equals(term)) {
                    freq++;
                    break;
                }
            }
        }
        return Math.log(size / (double) (1 + freq));
    }

    public static double getTfIdfScore(String term, List<Document> allDocs, Document doc) {
        double termFrequencyScore = getTermFrequencyScore(term, doc);
        double inverseDocumentFrequencyScore = getInverseDocumentFrequencyScore(term, allDocs);
        return termFrequencyScore * inverseDocumentFrequencyScore;
    }

}

public class DocumentRelevancyCalculator {
    private final List<Document> documents;

    public DocumentRelevancyCalculator(List<Document> documents) {
        this.documents = documents;
    }


    public List<DocumentTermScore> getRelevantDocuments(String query, int k) {
        List<DocumentTermScore> relatedDocs = new ArrayList<>();
        for(final Document doc : documents) {
            String[] terms = query.split("\\s+");
            double tfidfScore = 0;
            for(final String term : terms) {
                tfidfScore += RelevancyScoreUtils.getTfIdfScore(term, documents, doc);
            }
            if (tfidfScore > 0) {
                DocumentTermScore dtc = new DocumentTermScore(query, doc, tfidfScore);
                relatedDocs.add(dtc);
            }
        }
        relatedDocs.sort((d1, d2) -> -1 * Double.compare(d1.relScore(), d2.relScore()));
        return relatedDocs.subList(0, k);
    }
}






