$(function(){
    //カウント用の関数を定義
    function getLen(str){
        let result = 0;
        for(var i=0;i<str.length;i++){
            let chr = str.charCodeAt(i);
            if((chr >= 0x00 && chr < 0x81) ||
                (chr === 0xf8f0) ||
                (chr >= 0xff61 && chr < 0xffa0) ||
                (chr >= 0xf8f1 && chr < 0xf8f4)){
                //半角文字の場合は1を加算
                result += 1;
            }else{
                //それ以外の文字の場合は2を加算
                result += 1;
            }
        }
        //結果を返す
        return result;
    };
 
    //tweet
    $('.tweet-box').on('input', function(){
        //文字数を取得
        let cnt = getLen($(this).val())
        //文字数を元に計算
        let cnt_bar = cnt * 0.39;
        // グラフに反映する計算式
        let dash_array = 56.5 + cnt_bar;
        let circle = $('#circle');
        let submit_btn = $('.tweet-button');

        // stroke-dasharrayにdash_arrayの数値を反映
        circle.css('stroke-dasharray',dash_array);
        $('.now_cnt').text(cnt);//現在の文字数を表示

        if(cnt == 0){                       //0文字
            //submit_btn.prop('disabled', true);  // ボタン無効
            $('.count_circle').hide();           // circleを隠す

        } else if(cnt > 0 && 120 > cnt){            // 1文字以上、120文字以内
            $('.count_circle').fadeIn();               // circleを表示
            //submit_btn.prop('disabled', false);
            circle.attr('stroke','red');

            $('.cnt_area').removeClass('cnt_danger');// ボタン有効化
            $('.cnt_area').removeClass('cnt_alert'); // 文字色を戻す
            circle.attr('stroke','#1DA1F2');          // errorから復帰した時、circleの色を青に戻す

        } else if(cnt > 119 && 141 > cnt){            // 1文字以上、280文字以内
            $('.count_circle').fadeIn();               // circleを表示
            //submit_btn.prop('disabled', false);
            circle.attr('stroke','orange');            // circleを赤色
            $('.cnt_area').addClass('cnt_alert');
            $('.cnt_area').removeClass('cnt_danger'); // 文字色を戻す
            circle.attr('stroke','orange');          // errorから復帰した時、circleの色を青に戻す

        } else {                                  // 280文字を超える場合
            circle.attr('stroke','red');            // circleを赤色
            $('.cnt_area').addClass('cnt_danger');
            $('.cnt_area').removeClass('cnt_alert');// 文字色を赤色
            //submit_btn.prop('disabled', true);      // ボタン無効
        }
    });
    $('.tweet-box').trigger('input');


    //返信用カウント
    $('.retweet-box').on('input', function(){
        //文字数を取得
        let cnt = getLen($(this).val())
        let cnt_bar = cnt * 0.39;     //文字数を元に計算
        let dash_array = 56.5 + cnt_bar;  // グラフに反映する計算式
        let circle = $(this).next().children('.count').children('.retweet_count_circle').children('.retweet_circle_svg').children('.retweet_circle');
        let cnt_area = $(this).next().children('.count').children('.retweet_cnt_area');
        let cnt_circle = $(this).next().children('.count').children('.retweet_count_circle');
        console.log(cnt_circle)
        //let submit_btn = $('.tweet-button');

        // stroke-dasharrayにdash_arrayの数値を反映
        circle.css('stroke-dasharray',dash_array);
        //現在の文字数を表示
        $('.retweet_now_cnt').text(cnt);
//        $(this).next().children('.count')
//            .children('.retweet_cnt_area')
//            .children('.retweet_now_cnt').text(cnt);


        if(cnt == 0){
            console.log(0);//0文字
            //submit_btn.prop('disabled', true);  // ボタン無効
            cnt_circle.hide();
            cnt_area.hide();        // circleを隠す
            //$('.retweet_cnt_area').hide();        // circleを隠す

        } else if(cnt > 0 && 120 > cnt){            // 1文字以上、120文字以内
            //$(this).next(".retweet_count_circle").fadeIn();
            //$(this).next(".retweet_cnt_area").fadeIn();
            cnt_circle.fadeIn();
            cnt_area.fadeIn();
            // circleを表示
            //submit_btn.prop('disabled', false);
            circle.attr('stroke','red');

            cnt_area.removeClass('cnt_danger');// ボタン有効化
            cnt_area.removeClass('cnt_alert'); // 文字色を戻す
            circle.attr('stroke','#1DA1F2');          // errorから復帰した時、circleの色を青に戻す

        } else if(cnt > 119 && 141 > cnt){            // 1文字以上、280文字以内
            cnt_circle.fadeIn();
            cnt_area.fadeIn();      // circleを表示
            //submit_btn.prop('disabled', false);
            circle.attr('stroke','orange');            // circleを赤色
            cnt_area.addClass('cnt_alert');
            cnt_area.removeClass('cnt_danger'); // 文字色を戻す
            circle.attr('stroke','orange');          // errorから復帰した時、circleの色を青に戻す

        } else {                                  // 280文字を超える場合
            circle.attr('stroke','red');            // circleを赤色
            cnt_area.addClass('cnt_danger');
            cnt_area.removeClass('cnt_alert');// 文字色を赤色
            //submit_btn.prop('disabled', true);      // ボタン無効
        }
    });
    $('.retweet-box').trigger('input');
});