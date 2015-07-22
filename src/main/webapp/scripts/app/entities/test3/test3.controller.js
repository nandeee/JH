'use strict';

angular.module('jhipsterApp')
    .controller('Test3Controller', function ($scope, Test3) {
        window.scope = $scope;
        $scope.test3s = [];
        $scope.loadAll = function() {
            Test3.query(function(result) {
               $scope.test3s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test3.get({id: id}, function(result) {
                $scope.test3 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test3.id != null) {
                Test3.update($scope.test3,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test3.save($scope.test3,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.test3 = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Test3.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test3 = {one: null, two: null, three: null, four: null, five: null, six: null, seven: null, id: null};

        $scope.clear = function () {
            $scope.test3 = {one: null, two: null, three: null, four: null, five: null, six: null, seven: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

        };

        $scope.gridOptions = {
            data: 'test3s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test3.update(rowEntity,
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
                field: "four",
                displayName: "four"

            }, {
                field: "five",
                displayName: "five"

            }, {
                field: "six",
                displayName: "six"

            }, {
                field: "seven",
                displayName: "seven"


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
