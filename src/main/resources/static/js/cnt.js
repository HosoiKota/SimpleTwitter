$(function(){
    // tweet comment 入力文字数インジケーター
    $('textarea').on('input', function(){
        // 文字数を取得
        let cnt = $(this).val().length;
        let max = 140;
        let percent = Math.min((cnt / max) * 100, 100);

        // 個々のインジケーターを対象に
        const $parent = $(this).parent();
        const $circle_bar = $parent.find('.circle-bar');
        const $now_cnt = $parent.find('.now-cnt');
        const $count_circle = $parent.find('.count-circle');

        $circle_bar.css('--percent', percent);
        $now_cnt.text(cnt);

        const $target = $circle_bar.add('cnt-area');
        $target.removeClass('cnt-alert cnt-danger');

        if (cnt === 0) {
            $count_circle.hide();
        } else {
            $count_circle.show();
            if (cnt >= max) {
                $target.addClass('cnt-danger');
            } else if (cnt >= max - 20) {
                $target.addClass('cnt-alert');
            }
        }
    });
    $('textarea').trigger('input');
});