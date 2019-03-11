'use strict';

/* Controllers */

  // bootstrap controller
  app.controller('HeaderCtrl',['$scope', '$http', 'myStorage', '$state', function($scope, $http, myStorage, $state){
	  
	  $scope.user = myStorage.get('auth.user');
	  
	  $scope.logout = function(){
	     $http({
			 method: "GET",
			 url: $scope.app.url_user+"manager/logout",
			 params: {}
		 })
		 .success(function(response){
			if(response.code == 1){
				$state.go('access.signin');
				return false;
			}else{
				alert(response.msg);
				return false;
			}
		 })
		 .error(function(data){
			alert("网络故障，请稍后重试");
			return false;
		 });
	  }
  }])
  ; 
  app.controller('ShowPrescriptionModalInstanceCtrl', ['$scope', '$modalInstance', '$http', '$state', 'prescription', function($scope, $modalInstance, $http ,$state, prescription) {
    $scope.prescription = prescription;
	$scope.drugList = prescription.drugList;
	 
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
  }])
  ;
  app.controller('updateStoreAccountModalInstanceCtrl', ['$scope', '$modalInstance', '$http', '$state', 'store', function($scope, $modalInstance, $http ,$state, store) {
    $scope.store = store;
	$scope.update = {type:1};
	
	$scope.ok = function(){
      $http({
		  method: "POST",
		  url: $scope.app.url_prescription+"account/updateStoreBalance",
		  data: {
			storeid: $scope.store.storeid,
			type: $scope.update.type,
			amount: $scope.update.amount*100
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
	};
	  
	  $scope.cancel = function () {
		  $modalInstance.dismiss('cancel');
		};
    }])
	;
  app.controller('AddDoctorModalInstanceCtrl', ['$scope', '$modalInstance', '$http', '$state', 'Md5','hospital', function($scope, $modalInstance, $http ,$state, Md5, hospital) {
    $scope.doctor = {};
	
	$scope.ok = function(){
		var pwd = Md5.b64_hmac_md5("hk",$scope.doctor.password);//使用md5对密码加密,并转换为HEX
		
      $http({
		  method: "POST",
		  url: $scope.app.url_user+"doctor/add",
		  data: {
			name: $scope.doctor.name,
			department: $scope.doctor.department,
			phone: $scope.doctor.phone,
			password: pwd,
			hospitalid: hospital.id
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
	};
	  
	  $scope.cancel = function () {
		  $modalInstance.dismiss('cancel');
		};
    }])
	;
	app.controller('ModifyDoctorModalInstanceCtrl', ['$scope', '$modalInstance', '$http', '$state', 'Md5','doctor', function($scope, $modalInstance, $http ,$state, Md5, doctor) {
    $scope.doctor = doctor;
	
	$scope.ok = function(){

      $http({
		  method: "POST",
		  url: $scope.app.url_user+"doctor/modify",
		  data: {
			jsdoctor: JSON.stringify($scope.doctor)
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
	};
	  
	  $scope.cancel = function () {
		  $modalInstance.dismiss('cancel');
		};
    }])
	;
	app.controller('ModifyHospitalDrugModalInstanceCtrl', ['$scope', '$modalInstance', '$http', '$state','drug', function($scope, $modalInstance, $http ,$state, drug) {
    $scope.drug = drug;
	
	$scope.ok = function(){

      $http({
		  method: "POST",
		  url: $scope.app.url_drug+"hospital/modify",
		  data: {
			jsdrug: JSON.stringify($scope.drug)
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
	};
	  
	  $scope.cancel = function () {
		  $modalInstance.dismiss('cancel');
		};
    }])
	;
  app.controller('AddHospitalModalInstanceCtrl', ['$scope', '$modalInstance', '$http', 'Md5', function($scope, $modalInstance, $http, Md5) {
    $scope.hospital = {name: '',
					address: '',
					longitude : '',
					latitude : '',
					email : '',
					password : ''
					};
	
	$scope.ok = function(){
		var pwd = Md5.b64_hmac_md5("hk",$scope.hospital.password);//使用md5对密码加密,并转换为HEX
		
      $http({
		  method: "POST",
		  url: $scope.app.url_user+"hospital/addHospital",
		  data: {
			name: $scope.hospital.name,
			address: $scope.hospital.address,
			longitude: $scope.hospital.longitude,
			latitude: $scope.hospital.latitude,
			email: $scope.hospital.email,
			password: pwd
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
	};
	  
	  $scope.cancel = function () {
		  $modalInstance.dismiss('cancel');
		};
    }])
	;
  app.controller('ModifyHospitalModalInstanceCtrl', ['$scope', '$modalInstance', '$http', '$log', 'hospital', 'index', function($scope, $modalInstance, $http, $log, hospital, index) {

	$scope.hospital = hospital;	

    $scope.ok = function () {
	  $http({
		  method: "POST",
		  url: $scope.app.url_user+"hospital/modifyHospital",
		  data: {
			 jshospital: JSON.stringify($scope.hospital)
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
	};

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
  }])
  ; 
  app.controller('AddStoreModalInstanceCtrl', ['$scope', '$modalInstance', '$http', 'Md5', function($scope, $modalInstance, $http, Md5) {
    $scope.store = {};
	
	$scope.ok = function(){
		
		var pwd = Md5.b64_hmac_md5("hk",$scope.store.password);//使用md5对密码加密,并转换为HEX
		
		
      $http({
		  method: "POST",
		  url: $scope.app.url_user+"store/register",
		  data: {
			name: $scope.store.name,
			address: $scope.store.address,
			longitude: $scope.store.longitude,
			latitude: $scope.store.latitude,
			email: $scope.store.email,
			password: pwd,
			rate: $scope.store.rate
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
	
	
  }])
  ; 
  app.controller('ModifyStoreModalInstanceCtrl', ['$scope', '$modalInstance', '$http', '$log', 'store', 'index', function($scope, $modalInstance, $http, $log, store, index) {

	$scope.store = store;	

    $scope.ok = function () {
	  $http({
		  method: "POST",
		  url: $scope.app.url_user+"store/modify",
		  data: {
			jsstore: JSON.stringify($scope.store)
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
	};

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
  }])
  ; 
  app.controller('AddArticleModalInstanceCtrl', ['$scope', '$modalInstance', '$http', function($scope, $modalInstance, $http) {
    $scope.article = {};
	
	
	$scope.ok = function(){
		var picList = [];
		if($scope.article.pic0 !=null && $scope.article.pic0.length>0){
			picList.push($scope.article.pic0);
		}
		if($scope.article.pic1 !=null && $scope.article.pic1.length>0){
			picList.push($scope.article.pic1);
		}
		if($scope.article.pic2 !=null && $scope.article.pic2.length>0){
			picList.push($scope.article.pic2);
		}
		
		
      $http({
		  method: "POST",
		  url: $scope.app.url_user+"article/publish",
		  data: {
			title: $scope.article.title,
			piclist: JSON.stringify(picList),
			content: $scope.article.content
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
	
	
  }])
  ;
  app.controller('ModifyArticleModalInstanceCtrl', ['$scope', '$modalInstance', '$http', 'article', function($scope, $modalInstance, $http, article) {
    $scope.article = article;
	
	if(article.piclist.length == 1){
		$scope.article.pic0 = article.piclist[0];
	}else if(article.piclist.length == 2){
		$scope.article.pic0 = article.piclist[0];
		$scope.article.pic1 = article.piclist[1];
	}else if(article.piclist.length == 3){
		$scope.article.pic0 = article.piclist[0];
		$scope.article.pic1 = article.piclist[1];
		$scope.article.pic2 = article.piclist[2];
	}
	
	
	$scope.ok = function(){
		var picList = [];
		if($scope.article.pic0 !=null && $scope.article.pic0.length>0){
			picList.push($scope.article.pic0);
		}
		if($scope.article.pic1 !=null && $scope.article.pic1.length>0){
			picList.push($scope.article.pic1);
		}
		if($scope.article.pic2 !=null && $scope.article.pic2.length>0){
			picList.push($scope.article.pic2);
		}
      $http({
		  method: "POST",
		  url: $scope.app.url_user+"article/modify",
		  data: {
			articleId: $scope.article.articleid,
			title: $scope.article.title,
			piclist: JSON.stringify(picList),
			content: $scope.article.content
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
	
	
  }])
  ;  
  app.controller('ModifyDrugModalInstanceCtrl', ['$scope', '$modalInstance', '$http', 'drug', function($scope, $modalInstance, $http, drug) {

	$scope.drug = drug;	

    $scope.ok = function () {
	  $http({
		  method: "POST",
		  url: $scope.app.url_drug+"drug/modifyDrug",
		  data: {
			jsdrug: JSON.stringify($scope.drug)
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			$modalInstance.close(response.data);
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
	};

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
  }])
  ; 