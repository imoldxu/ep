
define(['app','angular'], function(app,angular){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window,$location,$rootScope,dataVer,$state){

        //默认值

		$scope.diagnosisMod = false; //控制诊断信息搜索框显示
		
        //$scope.modSel = false; 

        $scope.userArry = dataVer.get('userInfo'); //从医院获取的信息

        $scope.docArray = dataVer.get('docArray'); //缺省的医生信息


        var date = new Date();

        $scope.dateTime = date.getFullYear() +"/"+(date.getMonth()+1) + '/' + date.getDate();

        $scope.modal = false; //控制药品选择弹窗的

        $scope.placeholder = '请输入药品的拼音首字母或药品名称选择药品';

		//药品数量选择
        $scope.selNum = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"];

		$scope.jiLiang = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"];
		
        $scope.drugTable  = dataVer.get('drugInfo') || []; //药品列表

        $scope.sel = {};

        $scope.drugIndex; //药品在药品列表中的下标

        $scope.ipt ={

            "num":$scope.userArry.num,
            "doctorname":$scope.userArry.doctorname == null ? $scope.docArray == null ? '' :  $scope.docArray.doctorname : $scope.userArry.doctorname,
            "department":$scope.userArry.department == null ? $scope.docArray == null ? '' :  $scope.docArray.department : $scope.userArry.department,
            "diagnosis":$scope.userArry.diagnosis,
            "patientname":$scope.userArry.patientname,
            "patientage":$scope.userArry.patientage,
            "patientsex":$scope.userArry.patientsex,
            "patientphone":$scope.userArry.patientphone,
			"type":2,//中药
			"zyusage":$scope.userArry.zyusage,
			"zynum":$scope.userArry.zynum,
			"zysingledose":$scope.userArry.zysingledose,
			"zyfrequence":$scope.userArry.zyfrequence,
			"zymode":$scope.userArry.zymode,
        };
		
		$scope.drugArry = {};

        $scope.goLogin = function(){

            $state.go('welcome')

        };


        $scope.openModal = function(t){


            //if ($scope.drugTable.length >= 8){
            //    alert('超多只能添加8条数据');
            //    return false
            //}

			if($scope.ipt.doctorname == null || $scope.ipt.doctorname == ''){
				alert("请输入医生姓名");
				return false;
			}
			if($scope.ipt.department == null || $scope.ipt.department == ''){
				alert("请选择科室");
				return false;
			}
			
            $scope.drugIndex = t;

			$scope.sel.keyValue = '';
				
            $scope.placeholder = '请输入药品的拼音简称选择药品';
				
            if (t>=0){

                $scope.drugArry = $scope.drugTable[t];

				//$scope.sel.keyValue = $scope.drugArry.drugname;
				
                $scope.drugArry.id = $scope.drugArry.drugid;
				$scope.sel.n = $scope.drugArry.number;
				
				$scope.sel.usage = $scope.drugArry.myusage;
				
            }else {
				//新增
                $scope.drugArry = {};	
				
				$scope.sel.n = '1';
				
				$scope.sel.usage = '';
				
            }

			$scope.getDrugsByDoctor($scope.ipt.doctorname, $scope.ipt.department);//初始化获取我的常用药品
			
            $scope.modal = true;

        };

        $scope.closeModal = function(){

            $scope.modal = false;

        };

		
		$scope.getDrugsByDoctor = function(doctor, depart){
			$http({
                method: 'get',
                url: URL+'drug/getMyDrugInfoList',
                requestType: 'json',
                params: {
                    doctorname: doctor,
					department: depart,
					type: 2,
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
					type: 2,
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
                url: URL+'prescition/getDiagnosisByKeys',
                requestType: 'json',
                params: {
                    keys: keyword
                }
            })
            .success(function(data){

				if(data.code == 1){
                        $scope.diagnosisMsgListArry = data.data;

                        
                        $scope.diagnosisMod = true;
				}else{
					
					alert(data.msg);
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
					type: 2,
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
			
			if($scope.ipt.doctorname == null || $scope.ipt.doctorname == ''){
				alert("请输入医生姓名");
				return false;
			}
			if($scope.ipt.department == null || $scope.ipt.department == ''){
				alert("请选择科室");
				return false;
			}
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
			if($scope.ipt.zynum == null || $scope.ipt.zynum == '' || $scope.ipt.zynum == 0){
				alert("请选择中药剂数");
				return false;
			}
			if($scope.ipt.zyfrequence == null || $scope.ipt.zyfrequence == ''){
				alert("请选择每日剂量");
				return false;
			}
			if($scope.ipt.zyusage == null || $scope.ipt.zyusage == ''){
				alert("请选择中药用法");
				return false;
			}
			if($scope.ipt.zymode == null || $scope.ipt.zymode == ''){
				alert("请选择服用方式");
				return false;
			}
			if($scope.drugTable == null || $scope.drugTable == ''){
				alert("请选择至少一种药品");
				return false;
			}
			
			dataVer.put('userInfo',$scope.ipt);

			dataVer.put('drugInfo',$scope.drugTable);
						
			$state.go('zyprint');

        }

        $scope.delDrug = function(i){


            $scope.drugTable.splice(i,1)

        }

        $scope.addDrugInfo = function (){

			if($scope.drugArry.id ==null || $scope.drugArry.id==''){
				alert("请先选中药品再添加");
				return false;
			}

            var drugInfo = {
				drugid:$scope.drugArry.id,

                drugname:$scope.drugArry.drugname,

                standard :$scope.drugArry.standard,

                category:$scope.drugArry.category,

                price:$scope.drugArry.price,

                unit:$scope.drugArry.unit,

                number:$scope.sel.n,

                singledose:$scope.drugArry.singledose,

                myusage:$scope.sel.usage,

                frequency:''

                //sig: 'sig:'+ $scope.selY +','+ $scope.drugArry.singledose +',' +$scope.selP

            };

            if ($scope.drugIndex >=0){

                $scope.drugTable[$scope.drugIndex] = drugInfo

            }else  {

                $scope.drugTable.push(drugInfo);

                $scope.drugArry = {};

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

				if(data.code==1){
                        $scope.drugArry = data.data;

                        //$scope.sel.p = $scope.drugArry.frequency;

                        //$scope.sel.y = $scope.drugArry.defaultusage;

                        //$scope.ipt.drug = '';

                        $scope.placeholder = $scope.drugArry.drugname;

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

                    $scope.ipt.zyfrequence = x;

                    $scope.pMod = false;

                    break;
                case 'y':

                    $scope.ipt.zymode = x;

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

        var p = ['早上1次|zs1c|zaoshang1ci','中午1次|zw1c|zhongwu1ci','晚上1次|ws1c|wanshang1ci','分早晚2次|fzw2c|fenzaowan2ci','分早中晚3次|fzzw3c|fenzaozhongwan3ci'];
        var y = ['每日1剂|mr1j|meiri1ji','每日2剂|mr2j|meiri2ji','每日3剂|mr3j|meiri3ji'];


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
