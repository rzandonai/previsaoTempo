var app = angular.module('myApp', [ 'ngGrid', 'ui.grid', 'ngAnimate'
		 ]);
app.controller('TempoCtrl', function($scope, $http) {

	$http.get('./rest/weather').success(function(response) {
		$scope.myData = response;
	});

	$scope.title = "Exemplo tempo";

	$scope.gridOptions = {
		data : 'myData',
		columnDefs : [ {
			field : 'data',
			displayName : 'Data',
			cellFilter: "date:'yyyy-MM-dd HH:mm'" 
		}, {
			field : 'condicaoDoTempo',
			displayName : 'Condição do tempo'
		}, {
			field : 'humidadeRelativa',
			displayName : 'Humidade Relativa'
		}, {
			field : 'pontoDeCondensacao',
			displayName : 'Ponto de condensação'
		}, {
			field : 'pressaoAtmosferica',
			displayName : 'Pressão Atmosferica'
		}, {
			field : 'temperatura',
			displayName : 'Temperatura'
		}, {
			field : 'vento',
			displayName : 'Direção do vento'
		}, {
			field : 'visibilidade',
			displayName : 'Visibilidade'
		} ]
	};

});