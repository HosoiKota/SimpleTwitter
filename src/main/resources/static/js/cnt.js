$(function(){
    // tweet comment 入力文字数インジケーター
    $('.tweet-box').on('input', function(){
        // 文字数を取得
        let cnt = $(this).val().length;
        let max = 140;
        let percent = Math.min((cnt / max) * 100, 100);

        // 個々のインジケーターを対象に
        const $parent = $(this).parent();
        const $circle_bar = $parent.find('.circle_bar');
        const $now_cnt = $parent.find('.now_cnt');
        const $count_circle = $parent.find('.count_circle');

        $circle_bar.css('--percent', percent);
        $now_cnt.text(cnt);

        const $target = $circle_bar.add('cnt_area');
        $target.removeClass('cnt_alert cnt_danger');

        if (cnt === 0) {
            $count_circle.hide();
        } else {
            $count_circle.show();
            if (cnt >= max) {
                $target.addClass('cnt_danger');
            } else if (cnt >= max - 20) {
                $target.addClass('cnt_alert');
            }
        }
    });
    $('.tweet-box').trigger('input');
});