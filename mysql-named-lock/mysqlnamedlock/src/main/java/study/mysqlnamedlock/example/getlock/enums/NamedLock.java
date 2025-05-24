package study.mysqlnamedlock.example.getlock.enums;

import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NamedLock {
    GET_LOCK(1, 0, 0), RELEASE_LOCK(1, 0, null);

    private final Integer success;
    private final Integer fail;
    private final Integer notExist;

    public String getResult(Integer result) {
        if(Objects.equals(this.success, result)) {
            return "성공";
        }else if(Objects.equals(this.fail , result)) {
            return "실패";
        }
        return "존재 X";
    }
}
