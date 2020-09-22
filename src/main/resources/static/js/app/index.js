var main={
    init : function() {
        var _this = this;
        $("#btn-save").on("click", function() {
            _this.save();
        });
        $("#btn-update").on("click", function() {
            _this.update();
        });
    },
    save : function() {
        var data={
            title: $("#title").val(),
            author: $("#author").val(),
            content: $("#content").val()

        };
        $.ajax({
            type: "POST",
            url: "/api/s1/posts",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data)
        }).done(function(){
            alert("글 등록");
            location.href="/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    update : function() {
        var data={
            title: $("#title").val(),
            author: $("#author").val(),
            content: $("#content").val()

        };

        var id=$("#id").val();

        $.ajax({
            type:"PUT",
            url:"/api/s1/posts/"+id,
            dataType:"json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data)
        }).done(function(){
            alert("수정");
            location.href="/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        var id=$("#id").val();

        $.ajax({
            type:"DELETE",
            url:"/api/s1/posts/"+id,
            dataType:"json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data)
        }).done(function(){
            alert("삭제완료");
            location.href="/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
};

main.init();