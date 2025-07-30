package com.example.xJava8.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * リクエスト内で発生したNGチェックのバリデーションにおいて
 * 2回目以降のNG検知を判別し、1つのリクエスト内で2つ以上NG検知があった場合、usersテーブルにカウントする回数は1回になるようにする
 *
 */
@RequestScope
@Component
public class NgCheckRequestCounter {
    private int ngValidatorCount = 0;

    public int getCount() {
        return ngValidatorCount;
    }

    public void incrementCount() {
        ngValidatorCount++;
    }
}
