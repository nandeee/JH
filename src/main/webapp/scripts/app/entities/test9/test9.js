'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test9', {
                parent: 'entity',
                url: '/test9s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test9.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test9/test9s.html',
                        controller: 'Test9Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test9');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test9.detail', {
                parent: 'entity',
                url: '/test9/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test9.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test9/test9-detail.html',
                        controller: 'Test9DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test9');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Test9', function($stateParams, Test9) {
                        return Test9.get({id : $stateParams.id});
                    }]
                }
            })
            .state('test9.new', {
                parent: 'test9',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test9/test9-dialog.html',
                        controller: 'Test9DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, age: null, date: null, time: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('test9', null, { reload: true });
                    }, function() {
                        $state.go('test9');
                    })
                }]
            })
            .state('test9.edit', {
                parent: 'test9',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test9/test9-dialog.html',
                        controller: 'Test9DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Test9', function(Test9) {
                                return Test9.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('test9', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
