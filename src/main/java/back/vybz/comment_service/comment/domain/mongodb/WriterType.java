package back.vybz.comment_service.comment.domain.mongodb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum WriterType {

USER("사용자", "user"),
BUSKER("버스커", "busker");
    private final String description;
    private final String collectionName;
}
