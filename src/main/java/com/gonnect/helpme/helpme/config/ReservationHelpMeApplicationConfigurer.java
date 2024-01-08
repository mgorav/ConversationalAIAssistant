package com.gonnect.helpme.helpme.config;

import com.gonnect.helpme.helpme.agent.ReservationSupportAgent;
import com.gonnect.helpme.helpme.service.ReservationToolService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;


@Configuration
public class ReservationHelpMeApplicationConfigurer {

    /**
     * Run ReservationSupportApplicationTest to see simulated conversation with customer support agent
     */

    @Bean
    ReservationSupportAgent reservationSupportAgent(ChatLanguageModel chatLanguageModel,
                                                 ReservationToolService reservationToolService,
                                                 Retriever<TextSegment> retriever) {
        return AiServices.builder(ReservationSupportAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(reservationToolService)
                .retriever(retriever)
                .build();
    }

    @Bean
    Retriever<TextSegment> fetch(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {

        // You will need to adjust these parameters to find the optimal setting, which will depend on two main factors:
        // - The nature of your data
        // - The embedding model you are using
        int maxResultsRetrieved = 1;
        double minScore = 0.6;

        return EmbeddingStoreRetriever.from(embeddingStore, embeddingModel, maxResultsRetrieved, minScore);
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    EmbeddingStore<TextSegment> embeddingStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader) throws IOException {

        // Embedding Store Setup
        // --------------------
        // For demonstration purposes, the embedding store is populated
        // dynamically instead of being pre-filled with application data.
        // This allows the code to run self-sufficiently for demos.

        // The first step is initializing an embedding store.
        // For this example we use an in-memory implementation.
        // This stores the vector representations of text for similarity lookups.
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // Load "Gonnect Support Bot" training guidelines as sample
        Resource resource = resourceLoader.getResource("classpath:gonnect-miles-terms-and-condition.txt");
        Document document = loadDocument(resource.getFile().toPath(), new TextDocumentParser());

        // Ingest Sample Document
        // ---------------------

        // 1. Split document into 100-token segments
        // 2. Convert text segments into vector embeddings
        // 3. Save embeddings in the store

        // The EmbeddingStoreIngestor automates this process of
        // analyzing text and populating the embedding store
        DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0, new OpenAiTokenizer(GPT_3_5_TURBO));
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(document);

        return embeddingStore;
    }
}
