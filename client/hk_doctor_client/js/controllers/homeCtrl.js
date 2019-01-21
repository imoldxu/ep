
define(['app','angular'], function(app,angular){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window,$location,$rootScope,dataVer,$state){

        //默认值

		$scope.diagnosisMod = false; //控制诊断信息搜索框显示
		
        //$scope.modSel = false; 

        $scope.prescription = dataVer.get('prescriptionInfo'); //从医院初始化的处方的信息

        $scope.doctor = dataVer.get('doctorInfo'); //医生信息

		$scope.drugTable  = dataVer.get('drugList') || []; //开药列表

		$scope.drugIndex; //药品在药品列表中的下标

        var date = new Date(); //初始化当前的时间

        $scope.dateTime = date.getFullYear() +"/"+(date.getMonth()+1) + '/' + date.getDate();

        $scope.modal = false; //控制药品选择弹窗的

        $scope.placeholder = '请输入药品的拼音首字母或药品名称选择药品';

        $scope.selNum = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"];//初始化选药数量

        $scope.sel = {};

        $scope.ipt ={ //初始化处方对象

            "sn":$scope.prescription.sn,
            "doctorname":$scope.doctor.name,
            "department":$scope.doctor.department,
            "diagnosis":$scope.prescription.diagnosis,
            "patientname":$scope.prescription.patientname,
            "patientage":$scope.prescription.patientage,
            "patientsex":$scope.prescription.patientsex,
            "patientphone":$scope.prescription.patientphone,
			"type":1,//西药
			"zyusage":"",
			"zynum":1,
			"zysingledoes":"",
			"zyfrequence":"",
			"zymode":"",
        };
		
		$scope.drugObj = {};//编辑的单个药品对象

        $scope.goIndex = function(){

            $state.go('index');

        };

        $scope.openDrugModal = function(index){//打开选药界面

            if ($scope.drugTable.length > 8){
                alert('一张处方最多可开8种药品');
                return false
            }
			if($scope.ipt.doctorname == null || $scope.ipt.doctorname == ''){
				alert("请输入医生姓名");
				return false;
			}
			if($scope.ipt.department == null || $scope.ipt.department == ''){
				alert("请选择科室");
				return false;
			}

            $scope.drugIndex = index;//记录选药的下标
			
			$scope.sel.keyValue = '';//初始化选药界面

			$scope.placeholder = '请输入药品的拼音简称选择药品';
			
            if (index>=0){//编辑药品则初始化选药界面

                $scope.drugObj = $scope.drugTable[t];
				
				$scope.drugObj.id = $scope.drugObj.drugid;//将drugid赋值给id

                $scope.sel.p = $scope.drugObj.frequency;

                $scope.sel.y = $scope.drugObj.myusage;

            }else {

                $scope.drugObj = {};//新添加药品
				
				$scope.drugObj.singledose = '';

                $scope.sel.p = '';

                $scope.sel.y = '';

            }

			$scope.getDrugsByDoctor();//初始化获取我的常用药品
			
            $scope.modal = true;

        };

        $scope.closeModal = function(){

            $scope.modal = false;

        };

		$scope.getDrugsByDoctor = function(){
			$http({
                method: 'get',
                url: URL+'drug/getMyDrugInfoList',
                requestType: 'json',
                params: {
                    doctorid: $scope.doctor.id,
					type: 1,
                }
            })
			.success(function(data){

                if (data.code = 1){

                    $scope.modSel = true;

                    $scope.optArry = data.data;
                }else{
					alert(data.msg);
				}

            })
			.error(function(data){
				
				alert('系统服务异常，请联系管理员');
				
			})
		}
		
		$scope.getDrugsByCategory = function(categoryArg){
			$http({
                method: 'get',
                url: URL+'drug/getDrugInfoListByCategory',
                requestType: 'json',
                params: {
                    category: categoryArg,
					type: 1,
                }
            })
			.success(function(data){

                if (data.code = 1){

                    //$scope.modSel = true;

                    $scope.optArry = data.data;
                }else{
					alert(data.msg);
				}

            })
			.error(function(data){
				
				alert('系统服务异常，请联系管理员');
				
			})
		}

		$scope.closeDiagnosisMode = function(){
			$scope.diagnosisMod = false;
		}
		
		$scope.selectDiagnosisMsg = function(msg){
			$scope.ipt.diagnosis = msg;
			$scope.diagnosisMod = false;
		}
		
		$scope.getDiagnosisMsg = function(keyword){
			
			if (keyword == '' || keyword.length < 1){
                $scope.diagnosisMod = false;
                return false;
            }
			
			 $http({
                method: 'get',
                url: URL+'diagnosis/getDiagnosisByKeys',
                requestType: 'json',
                params: {
                    keys: keyword
                }
            })
            .success(function(resp){

				if(resp.code == 1){
                    $scope.diagnosisMsgListArry = resp.data;
    
                    $scope.diagnosisMod = true;
				}else{
					
					alert(resp.msg);
				}

			})
			.error(function(data){
				
				alert('系统服务异常，请联系管理员');
				
			})
		};
		
        $scope.getDrugs = function(name){

            //if (name == '' || name.length <= 1){
            //    $scope.modSel = false;
            //    return false;
            //}

            $http({
                method: 'get',
                url: URL+'drug/getDrugsByKeys',
                requestType: 'json',
                params: {
                    keys: name,
					type: 1,
                }
            })
            .success(function(data){

                if (data.code == 1){

                    //$scope.modSel = true;

                    $scope.optArry = data.data;
                }else{
					alert(data.msg);
				}

            })
			.error(function(data){
				
				alert('系统服务异常，请联系管理员');
				
			})

        }
		
        $scope.nextP = function(){

			if($scope.ipt.patientname == null || $scope.ipt.patientname == ''){
				alert("请输入患者姓名");
				return false;
			}
			if($scope.ipt.patientsex == null || $scope.ipt.patientsex == ''){
				alert("请选择患者性别");
				return false;
			}
			if($scope.ipt.patientage == null || $scope.ipt.patientage == ''){
				alert("请输入患者年龄");
				return false;
			}
			if($scope.ipt.diagnosis == null || $scope.ipt.diagnosis == ''){
				alert("请输入诊断内容");
				return false;
			}
			if($scope.drugTable == null || $scope.drugTable == ''){
				alert("请选择至少一种药品");
				return false;
			}
		
			dataVer.put('prescriptionInfo',$scope.ipt);

			dataVer.put('drugList',$scope.drugTable);
						
			$state.go('print');
        }

        $scope.delDrug = function(i){


            $scope.drugTable.splice(i,1)

        }

        $scope.addDrugInfo = function (){

			if($scope.drugObj.id ==null || $scope.drugObj.id==''){
				alert("请先选中药品再添加");
				return false;
			}

            var drugInfo = {
				drugid:$scope.drugObj.id,

                drugname:$scope.drugObj.drugname,

                standard :$scope.drugObj.standard,

                category:$scope.drugObj.category,

                price:$scope.drugObj.price,

                unit:$scope.drugObj.unit,

                number:$scope.sel.n,

                singledose:$scope.drugObj.singledose,

                myusage:$scope.sel.y,

                frequency:$scope.sel.p

                //sig: 'sig:'+ $scope.selY +','+ $scope.drugObj.singledose +',' +$scope.selP

            };

            if ($scope.drugIndex >=0){

                $scope.drugTable[$scope.drugIndex] = drugInfo

            }else  {

                $scope.drugTable.push(drugInfo);

                $scope.drugObj = {};

                $scope.sel.p = '';

                $scope.sel.y = '';

                $scope.placeholder = '请输入药品的拼音简称选择药品';


            }

            $scope.modal = false

        }

		

        $scope.checkDrug = function(id){

            $http({
                method: 'get',
                url: URL+'drug/getDrugByID',
                requestType: 'json',
                params: {
                    drugid: id
                }
            })
            .success(function(data){

				if(data.code == 1){
                        $scope.drugObj = data.data;

                        $scope.sel.p = $scope.drugObj.frequency;

                        $scope.sel.y = $scope.drugObj.defaultusage;

                        $scope.ipt.drug = '';

                        $scope.placeholder = $scope.drugObj.drugname;

                        //$scope.modSel = false;
				}else{
					alert(data.msg);
				}
			})
			.error(function(data){
				
				alert('系统服务异常，请联系管理员');
				
			})

        };


        $scope.checkOpt = function(x,y){


            switch (y){

                case 'p':

                    $scope.sel.p = x;

                    $scope.pMod = false;

                    break;
                case 'y':

                    $scope.sel.y = x;

                    $scope.yMod = false;

                    break;
				//case 'diagnosis':

				//	var strs = $scope.ipt.diagnosis.split(/\s+/)
				//	strs[strs.length-1] = x;
					
				//	$scope.ipt.diagnosis = strs[0]; 
                //    angular.forEach(strs, function(data,index,array){
				//		if(index!=0){
				//			$scope.ipt.diagnosis = $scope.ipt.diagnosis+" "+data;
				//		}
				//	});
					
                //    $scope.diagnosisMod = false;

                //    break;

            }

        };

        var p = ['需要时使用|xyssy|xuyao','30分钟一次|30fz1c|30fenzhongyici','1小时1次|1xs1c|yixiaoshiyici','2小时1次|2xs1c|liangxiaoshiyici',
		         '1日1次|1r1c|yryc|yiriyici','1日2次|1r2c|yrlc|yiriliangci','1日3次|1r3c|yrsc|yirisanci',
		         '1日4次|1r4c|yrsc|yirisici','1日5次|1r5c|yrwc|yiriwuci','1日6次|1r6c|yrlc|yiriliuci',
				 '1周1次|1z1c|yzyc|yizhouyici','1周2次|1z2c|yzlc|yizhouliangci','1周3次|1z3c|yzsc|yizhousanci','1月1次|1y1c|yiyueyici',
				 '1季度1次|1jd1c|yjdyc|yijiduyici'];
        var y = ['口服|kf|koufu','餐前一小时空腹|cqyxskf|canqianyixiaoshikongfu','冲服|cf|chongfu','冲调|ct|chongtiao',
		         '滴眼|dy|diyan','滴注|dz|dizhu','早晨空腹|zckf|zaochenkongfu','早晨吞服|zctf|zaochentunfu','饭后口服|fhkf|fanhoukoufu','含服|hf|hanfu','含漱|hs|hanshu',
				 '肌内注射|jnzs|jineizhushe','肌肉或静脉注射|jrhjmzs|jirouhuojingmaizhushe','开水冲服|kscf|kaishuichongfu',
				 '空腹服用|kffy|koufufuyong','口服,外用|kfwy|koufuwaiyong','口腔喷雾|kqpw|kouqiangpenwu','内服|nf|内服',
				 '舌下含服|sxhf|shexiahanfu','舌下喷雾|sxpw|shexiapenwu','刷牙后含漱|syhhs|shuayahouhanshu','水煎服|sjf|shuijianfu',
				 '贴患处|thc|tiehuanchu','涂于眼睑内|tyyjn|tuyuyanjiannei','臀部肌肉注射|tbjrzs|tunbujirouzhushe',
				 '外用|wy|waiyong','阴道给药|ydgy|yindaogeiyao','直肠给药|zcgy|zhichanggeiyao','注射|zs|zhushe',
				 '注射或者静脉滴注|zshzjmdz|zhushehuozhejingmaidizhu','注射用|zsy|zhusheyong','口服或局部灌注|kfhjbgz|koufuhuojubuguanzhu','吹敷患处|cfhc|chuifuhuanchu',
				 '进餐时服用|jcsfy|jincanshifuyong','静脉滴注|jmdz|jingmaidizhu','静脉滴注或静脉推注|jmdzhjmtz|jingmaidizhuhuojingmaituizhu'];

	//	var diagnosisList = ['鼻息肉|bxr|bixirou','鼻窦炎|bdy|bidouyan'];		 

        $scope.optSerch = function (str,type){

            if (str == ''){

                $scope.yMod = false;

                $scope.pMod = false;
				
				//$scope.diagnosisMod = false;

                return false

            }

            var a;

            switch (type){

                case 'p':

                    a = p;

                    $scope.pMod = true;
					$scope.yMod = false;
					//$scope.diagnosisMod = false;

                    break;
                case 'y':

                    a = y;
					
					$scope.pMod = false;
                    $scope.yMod = true;
					//$scope.diagnosisMod = false;

                    break;
				//case 'diagnosis':
				
				    //var strs = str.split(/\s+/)
					//str = strs[strs.length-1];
					//a = diagnosisList;
				
					//$scope.pMod = false;
                    //$scope.yMod = false;
					//$scope.diagnosisMod = true;

            }

            $scope.optListArry = [];

            console.log(str);
			str = str.toString();

            angular.forEach(a, function(data,index,array){


                var dataArry = data.split('|');

                var t = testArry(dataArry,str);

                if (t == true){

                    $scope.optListArry.push(dataArry[0]);

                }

            });

            console.log($scope.optListArry)
        }

        function testArry(array,str){

            var t = false;

            var aa = angular.forEach(array, function(data,index,array){

                var test = data.indexOf(str);

                if (test >= 0){

                    t = true;
					return t;
                }


            });

            return t;


        }


    }];

});
