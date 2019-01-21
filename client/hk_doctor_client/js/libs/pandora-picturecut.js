

(function(window) {



    window.fn = function(query,list){
        return list == undefined ? document.querySelector(query) : document.querySelectorAll(query);
    };

    window.picture = function (pictureList){
        fn(pictureList.open).onclick = function(){
            craPictureModel(pictureList);
        }
    };
    function craPictureModel(fnList){

        // 建立蒙层
        var model = document.createElement("div");
        model.className = "pictureModel";
        document.body.appendChild(model);

        //选取图片
        var pandoraCut = document.createElement("div");
        pandoraCut.className = "pandoraCut";
        pandoraCut.style.width = fnList.modelStyle.width + "px";
        model.appendChild(pandoraCut);

        var upLoad = document.createElement("div");


        pandoraCut.innerHTML = '<div class="updateFile"><input type="file" id="dropFile"/><p>将图片拖拽到此处或者点击上传图片</p></div><div class="imgFile"><div class="imgCut"><canvas id="cutIn"></canvas></div><div class="cutMc"></div></div>' +
            '<div class="funBtn">' +
            '<div class="imgClose">取消</div><div class="imgFalse">完成</div> ' +
            '</div>';
        model.appendChild(pandoraCut);

        cutInit(fnList);

        fn(".imgClose").onclick = function (){
            document.body.removeChild(model);
        }
    }




    function cutInit(fnList){
        var file = fn("#dropFile");
        var imgFile = fn(".imgFile");
        var imgFalse = fn(".imgFalse");

        file.onchange = function(event){
            var img = event.target.files[0];
            if(! ( img.type.indexOf("image") == 0 ) ){
                alert('请上传图片');
                return false;
            }
            var reader = new FileReader();
            reader.readAsDataURL(img);
            reader.onload = function (e){
                var imgMax = fn(".pictureModel").offsetHeight;
                imgFile.style.display = "block";
                img = new Image();
                img.src = reader.result;
                if (img.height >= ( imgMax * 0.8 )){
                    img.height =  ( imgMax / 2 );
                }else{
                    img.width = imgFile.offsetWidth;
                }
                img.onload = function(){
                    imgFile.appendChild(img);
                    var canvas = document.createElement("canvas");
                    imgFile.appendChild(canvas);
                    var ctx = canvas.getContext('2d');
                    canvas.id = "cutCan";
                    canvas.width = imgFile.offsetWidth;
                    canvas.height = img.offsetHeight;
                    var daf = (imgFile.offsetWidth - img.offsetWidth)/2;
                    var imgW = img.offsetWidth;
                    var imgH = img.offsetHeight;
                    imgFile.removeChild(img);
                    var model = fn(".pandoraCut");
                    console.log(fn(".pictureModel").offsetHeight);
                    model.style.top = ( imgMax - model.offsetHeight ) / 2 +"px";
                    fn(".updateFile").style.display = "none";
                    ctx.drawImage(img,daf,0,imgW,imgH);
                    dorpDiv(fnList);
                    drawCutImg(fnList);
                    imgFalse.style.color = "black";

                };
            };
            imgFalse.onclick = function (){
                var canvas2 = document.querySelector("#cutIn");
                var model = document.querySelector(".pictureModel");
                //document.getElementById("imgSrc").value = canvas2.toDataURL();
                document.body.removeChild(model);
                fnList.callback(canvas2.toDataURL())
            };

        };




    }

    function drawCutImg(){
        var canvas =  document.querySelector("#cutCan");
        var imgCut =  document.querySelector(".imgCut");
        var canvas2 = document.querySelector("#cutIn");

        var ctx = canvas.getContext('2d');
        var x = imgCut.offsetLeft;
        var y = imgCut.offsetTop;
        var width = imgCut.offsetWidth;
        var height = imgCut.offsetHeight;
        var imgData= ctx.getImageData(x,y,width,height);
        var imgData= ctx.getImageData(x,y,width,height);
        var ctx2 = canvas2.getContext('2d');
        imgCut.appendChild(canvas2);
        canvas2.width = width;
        canvas2.height = height;
        ctx2.putImageData(imgData,0,0);


    }


    function dorpDiv(fnList){
        var imgCut =  fn(".imgCut");
        var pandoraCut =  fn(".imgFile");
        imgCut.addEventListener("mousedown",moveStart,false);
        document.addEventListener("mouseup",moveOver,false);
        document.addEventListener("mousemove",moving,false);

        if(fnList.modelStyle.cutWidth){
            imgCut.style.width = fnList.modelStyle.cutWidth + "px";
            imgCut.style.height = fnList.modelStyle.cutHeight + "px";
        }

        var width = imgCut.offsetWidth;
        var height = imgCut.offsetHeight;
        imgCut.style.left = (pandoraCut.offsetWidth - width) / 2 +"px";
        imgCut.style.top = (pandoraCut.offsetHeight - height) / 2 +"px";
        if(!fnList.modelStyle.fixed){
            for(var i = 0;  i <= 8; i++){
                var spanDiv = document.createElement("div");
                spanDiv.style.left = parseInt( i / 3 )  * ( width / 2 ) - 5 + "px";
                spanDiv.style.top = parseInt( i % 3 ) * ( height / 2 ) - 5 + "px";
                if ( i != 4 ){
                    imgCut.appendChild(spanDiv)
                }
                spanDiv.setAttribute("sp",i);
                spanDivDorp(spanDiv);
            }
        }



        var move = false;
        var spMove = false;
        var startX, startY,imgLeft,imgTop,imgWidth,imgHeight,maxLeft,maxTop,spNum,maxWidth,maxHeight,minWidth,minHeight;
        function moveStart(e){
            var imgCut =  document.querySelector(".imgCut");

            move = true;
            startX = e.clientX;
            startY = e.clientY;
            imgLeft = imgCut.offsetLeft;
            imgTop = imgCut.offsetTop;
            imgWidth = imgCut.offsetWidth;
            imgHeight = imgCut.offsetHeight;
            maxLeft =  pandoraCut.offsetWidth - imgWidth;
            maxTop =  pandoraCut.offsetHeight - imgHeight;
        }
        function moveOver(e){
            move = false;
        }
        function spanEnd(e){
            spMove = false;
            move = false;
        }
        function moving(e){
            if( move ==  false){
                return false;
            }
            var moveX = e.clientX - startX;
            var moveY = e.clientY - startY;

            var leftMove = moveX + imgLeft;
            var topMove = moveY + imgTop;

            maxLeft > leftMove && leftMove > 0 ? imgCut.style.left = leftMove + "px" : "";
            maxTop > topMove && topMove > 0 ? imgCut.style.top = topMove +"px" : "";
            drawCutImg();

        }

        function spanDivDorp(spanDiv){
            spanDiv.addEventListener("mousedown",spanStart,false);
            document.addEventListener("mousemove",spanMove,false);
            document.addEventListener("mouseup",spanEnd,false);
        }

        function spanStart(e){
            var imgCut = fn(".imgCut");
            var pandoraCut = fn(".imgFile");
            move = false;
            spMove = true;

            startX = e.clientX;
            startY = e.clientY;

            imgLeft = imgCut.offsetLeft;
            imgTop = imgCut.offsetTop;
            imgWidth = imgCut.offsetWidth;
            imgHeight = imgCut.offsetHeight;

            maxWidth = fnList.modelStyle.maxWidth || pandoraCut.offsetWidth;
            maxHeight = fnList.modelStyle.maxHeight || pandoraCut.offsetHeight;

            minWidth = fnList.modelStyle.minWidth || 0;
            minHeight = fnList.modelStyle.minHeight || 0;

            spNum  = this.getAttribute("sp");
            var ev = e || window.event;
            if(ev.stopPropagation){
                ev.stopPropagation();
            }
            else if(window.event){
                window.event.cancelBubble = true;
            }
            function nameOne(_this){
                var inNum  = _this.getAttribute("sp");
            }

        }

        function spanMove(e){
            e.preventDefault();

            if( spMove ==  false){
                return false;
            }



            var moveX = e.clientX - startX;
            var moveY = e.clientY - startY;
            var newImgLeft = imgCut.offsetLeft;
            var newImgTop = imgCut.offsetTop;
            var newImgWidth = imgCut.offsetWidth;
            var newImgHeight = imgCut.offsetHeight;

            //if ( newImgWidth + newImgLeft >= pandoraCut.offsetWidth || newImgHeight + newImgTop >= pandoraCut.offsetHeight){
            //    return false;
            //}
            //if (  newImgLeft <= 0 ||  newImgTop  <= 0  ){
            //    return false;
            //}
            //if (newImgWidth  >= fnList.modelStyle.maxWidth || newImgHeight + moveY >= fnList.modelStyle.maxHeight ){
            //    return false;
            //}
            //if (newImgWidth <= fnList.modelStyle.minWidth || newImgHeight <= fnList.modelStyle.minHeight ){
            //    return false;
            //}


            switch (spNum){
                case "0":
                    var left = imgLeft + moveX;
                    var top  = imgTop + moveY;
                    var width = imgWidth + ( -moveX );
                    var height = imgHeight + ( -moveY );
                    if (left + width >= pandoraCut.offsetWidth || left <= 0){
                        return false;
                    }
                    if(top + height >= pandoraCut.offsetHeight || top <= 0){
                        return false;
                    }
                    if(width >= maxWidth || width <= minWidth){
                        return false
                    }
                    if(height >= maxHeight || height <= minHeight){
                        return false
                    }
                    imgCut.style.left  = left + "px";
                    imgCut.style.top  = top + "px" ;
                    imgCut.style.width = width +"px";
                    imgCut.style.height = height +"px";
                    break;
                case "1":

                    var left = imgLeft + moveX;
                    var width = imgWidth + ( -moveX );
                    if (left + width >= pandoraCut.offsetWidth || left <= 0){
                        return false;
                    }
                    if(width >= maxWidth || width <= minWidth){
                        return false
                    }
                    imgCut.style.left  = left+ "px";
                    imgCut.style.width = width +"px";
                    break;
                case "2":
                    var left = imgLeft + moveX;
                    var width = imgWidth + ( -moveX );
                    var height = imgHeight + ( moveY );

                    if (left + width >= pandoraCut.offsetWidth || left <= 0){
                        return false;
                    }
                    if(width >= maxWidth || width <= minWidth){
                        return false
                    }
                    if(height >= maxHeight || height <= minHeight){
                        return false
                    }
                    imgCut.style.left  = left + "px";
                    imgCut.style.width = width +"px";
                    imgCut.style.height = height +"px";
                    break;
                case "3":
                    var top  = imgTop + moveY;
                    var height = imgHeight + ( moveY );
                    if(top + height >= pandoraCut.offsetHeight || top <= 0){
                        return false;
                    }
                    if(height >= maxHeight || height <= minHeight){
                        return false
                    }
                    imgCut.style.top  = top + "px";
                    imgCut.style.height = height +"px";
                    break;
                case "5":
                    var width = imgWidth + ( moveY );
                    if(width >= maxWidth || width <= minWidth){
                        return false
                    }
                    imgCut.style.height = width +"px";
                    break;
                case "6":
                    var top = imgTop + moveY;
                    var width = imgWidth + ( moveX );
                    var height = imgHeight + ( -moveY );

                    if(top + height >= pandoraCut.offsetHeight || top <= 0){
                        return false;
                    }
                    if(width >= maxWidth || width <= minWidth){
                        return false
                    }
                    if(height >= maxHeight || height <= minHeight){
                        return false
                    }

                    imgCut.style.top  = top + "px";
                    imgCut.style.width = width +"px";
                    imgCut.style.height = height +"px";
                    break;
                case "7":
                    var width = imgWidth + moveX;
                    if(width >= maxWidth || width <= minWidth){
                        return false
                    }
                    imgCut.style.width = width +"px";
                    break;
                case "8":
                    var width = imgWidth + ( moveX );
                    var height = imgHeight + ( moveY );
                    if(width >= maxWidth || width <= minWidth){
                        return false
                    }
                    if(height >= maxHeight || height <= minHeight){
                        return false
                    }
                    imgCut.style.width = width +"px";
                    imgCut.style.height = height +"px";
                    break
            }
            drawCutImg();

            spotXy();

            var ev = e || window.event;

            if(ev.stopPropagation){
                ev.stopPropagation();
            }
            else if(window.event){
                window.event.cancelBubble = true;
            }
        }

    }

    function spotXy(){
        var imgCut =  document.querySelector(".imgCut");
        var height = imgCut.offsetHeight;
        var width =imgCut.offsetWidth;

        var divList = document.querySelectorAll(".imgCut div");
        for(var i = 0;  i <  divList.length; i++){
            var sp = divList[i].getAttribute("sp");
            divList[i].style.left = parseInt( sp / 3 )  * parseInt( width / 2 ) - 5 + "px";
            divList[i].style.top = parseInt( sp % 3 ) * parseInt( height / 2 ) - 5 + "px";
        }
    }

}(window));