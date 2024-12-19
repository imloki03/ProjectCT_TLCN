package com.hcmute.projectCT.service;

import com.hcmute.projectCT.enums.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class MediaServiceImplTest {

    @InjectMocks
    private MediaServiceImpl mediaService;

    @BeforeEach
    void setUp() {
        mediaService = new MediaServiceImpl(null, null); // Mock dependencies if needed
    }

    @Test
    void convertFilenameToMediaType_shouldReturnVideo_forVideoFiles() {
        assertEquals(MediaType.VIDEO, mediaService.convertFilenameToMediaType("movie.mp4"));
        assertEquals(MediaType.VIDEO, mediaService.convertFilenameToMediaType("clip.avi"));
        assertEquals(MediaType.VIDEO, mediaService.convertFilenameToMediaType("show.mkv"));
    }

    @Test
    void convertFilenameToMediaType_shouldReturnImage_forImageFiles() {
        assertEquals(MediaType.IMAGE, mediaService.convertFilenameToMediaType("photo.jpg"));
        assertEquals(MediaType.IMAGE, mediaService.convertFilenameToMediaType("graphic.png"));
        assertEquals(MediaType.IMAGE, mediaService.convertFilenameToMediaType("animation.gif"));
    }

    @Test
    void convertFilenameToMediaType_shouldReturnDoc_forDocumentFiles() {
        assertEquals(MediaType.DOC, mediaService.convertFilenameToMediaType("document.pdf"));
        assertEquals(MediaType.DOC, mediaService.convertFilenameToMediaType("report.docx"));
    }

    @Test
    void convertFilenameToMediaType_shouldReturnPresentation_forPresentationFiles() {
        assertEquals(MediaType.PRESENTATION, mediaService.convertFilenameToMediaType("slides.ppt"));
        assertEquals(MediaType.PRESENTATION, mediaService.convertFilenameToMediaType("deck.pptx"));
    }

    @Test
    void convertFilenameToMediaType_shouldReturnWorkbook_forWorkbookFiles() {
        assertEquals(MediaType.WORKBOOK, mediaService.convertFilenameToMediaType("spreadsheet.xls"));
        assertEquals(MediaType.WORKBOOK, mediaService.convertFilenameToMediaType("data.xlsx"));
    }

    @Test
    void convertFilenameToMediaType_shouldThrowException_forUnsupportedFileTypes() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                mediaService.convertFilenameToMediaType("unsupported.xyz")
        );
        assertEquals("Unsupported file type: xyz", exception.getMessage());
    }
}