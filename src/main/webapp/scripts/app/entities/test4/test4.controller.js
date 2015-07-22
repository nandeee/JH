'use strict';

angular.module('jhipsterApp')
    .controller('Test4Controller', function ($scope, Test4) {
        window.scope = $scope;
        $scope.test4s = [];
        $scope.loadAll = function() {
            Test4.query(function(result) {
               $scope.test4s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test4.get({id: id}, function(result) {
                $scope.test4 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test4.id != null) {
                Test4.update($scope.test4,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test4.save($scope.test4,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.test4 = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Test4.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test4 = {one: null, two: null, three: null, id: null};

        $scope.clear = function () {
            $scope.test4 = {one: null, two: null, three: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

        };

        $scope.gridOptions = {
            data: 'test4s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test4.update(rowEntity,
                        function() {
                            $scope.refresh();
                        });
                });
            },
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "one",
                displayName: "one"

            }, {
                field: "two",
                displayName: "two"

            }, {
                field: "three",
                displayName: "three"


            }, {
                name: 'placeholder',
                displayName: '',
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                enableFiltering: false,
                headerCellTemplate: '<span></span>',
                cellClass: 'tableButton',
                cellTemplate: '<div class="tableButton"><button id="delBtn" type="button" class="btn-small" ng-click="grid.appScope.delete(row.entity.id)">Delete</button><button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity)">Edit</button></div>'
            }],
        };

        $scope.ctrl = {};
        $scope.ctrl.querySearch = querySearch;

        function querySearch(query, entity) {
            var results = query ? $scope[entity].filter(createFilterFor(query)) : $scope[entity];
            return results;
        }

        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return (state.name.toLowerCase().indexOf(lowercaseQuery) === 0);
            };
        }
    });
