package com.learntocodetogether.tfidf;

import com.learntocodetogether.tdidf.Document;
import com.learntocodetogether.tdidf.DocumentRelevancyCalculator;
import com.learntocodetogether.tdidf.DocumentTermScore;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author namvdo
 */
class DocumentRelevancyCalculatorTest {
    static DocumentRelevancyCalculator documentRelevancyCalculator;

    @BeforeEach
    public void init() {
        documentRelevancyCalculator = new DocumentRelevancyCalculator(List.of(
                new Document(1, "the chains of habit are too weak to be felt until they're too strong to be broken."),
                new Document(2, "think simple, then create actionable steps to process it."),
                new Document(3, "what is an obvious evidence that makes you think you have improved something in your life?"),
                new Document(4, "if you can do something in under 2 minutes, then just doing it right away might save you a lot of time."),
                new Document(5, "15 minutes of direct sunlight in the morning will save you hours of insomnia in the evening."))
        );
    }

    @Test
    void testGetRelevantDocuments() {
        List<DocumentTermScore> documents = documentRelevancyCalculator.getRelevantDocuments("think simple", 2);
        assertEquals(2, documents.get(0).doc().docId());
        assertEquals(3, documents.get(1).doc().docId());
    }
}
