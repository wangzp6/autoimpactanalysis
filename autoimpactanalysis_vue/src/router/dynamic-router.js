/* 需要权限判断的路由 */
const dynamicRoutes = [
    {
        path: 'projectdetail',
        name: 'projectdetail',
        component: () => import('../pages/dataretrievalsystem/reportstatistics/projectdetail'),
        meta: {
            name: '项目管理',
            icon: 'icon-quit'
        }
    },
    {
        path: 'reportdetail',
        name: 'reportdetail',
        component: () => import('../pages/dataretrievalsystem/reportstatistics/reportdetail'),
        meta: {
            name: '报表管理',
            icon: 'icon-quit'
        }
    },
    {
        path: 'documentdetail',
        name: 'documentdetail',
        component: () => import('../pages/dataretrievalsystem/reportstatistics/documentdetail'),
        meta: {
            name: '交付件管理',
            icon: 'icon-quit'
        }
    },
    {
        path: 'busconfirm',
        name: 'busconfirm',
        component: () => import('../pages/dataretrievalsystem/reportstatistics/busconfirm'),
        meta: {
            name: '问题确认管理',
            icon: 'icon-quit'
        }
    },
    {
        path: 'scriptquery',
        name: 'scriptquery',
        component: () => import('../pages/dataretrievalsystem/scriptquery'),
        meta: {
            name: '业务提数',
            icon: 'icon-nav'
        }
    },
    {
        path: 'doctypecode',
        name: 'doctypecode',
        component: () => import('../pages/dataretrievalsystem/reportstatistics/doctypecode'),
        meta: {
            name: '枚举值配置',
            icon: 'icon-quit'
        }
    },
    {
        path: '/test',
        name: 'test',
        component: () => import('../pages/test'),
        meta: {
            name: '测试页面',
            icon: 'icon-order-manage'
        },
        children: [
            {
                path: 'tinymce',
                name: 'tinymce',
                component: () => import('../pages/test/tinymce'),
                meta: {
                    name: '测试富文本',
                    icon: 'icon-quit'
                }
            },
            {
                path: 'filedownload',
                name: 'filedownload',
                component: () => import('../pages/test/filedownload'),
                meta: {
                    name: '测试文件下载',
                    icon: 'icon-quit'
                }
            },
            {
                path: 'itemdetail',
                name: 'itemdetail',
                component: () => import('../pages/dataretrievalsystem/reportstatistics/itemdetail'),
                meta: {
                    name: '报表元素管理',
                    icon: 'icon-quit'
                }
            }

        ]
    },
    {
        path: '/autoimpactanalysis',
        component: () => import('../pages/autoimpactanalysis'),
        name: 'autoimpactanalysis',
        meta: {
            name: '影响性分析自动化',
            icon: 'icon-order-manage'
        },
        children: [
            {
                path: 'fileupload',
                name: 'file-upload',
                component: () => import('../pages/autoimpactanalysis/fileupload'),
                meta: {
                    name: '数据导入及程序解析',
                    icon: 'icon-home'
                }
            },
            {
                path: 'coefficientmaintenance',
                name: 'coefficient-maintenance',
                component: () => import('../pages/autoimpactanalysis/coefficient-maintenance'),
                meta: {
                    name: '系数维护',
                    icon: 'icon-product-manage'
                }
            },
            {
                path: 'reportexport',
                name: 'reportexport',
                component: () => import('../pages/autoimpactanalysis/reportexport'),
                meta: {
                    name: '报表展示及导出',
                    icon: 'icon-product-manage'
                }
            }
        ]
    },
    {
        path: '/qualitymanager',
        component: () => import('../pages/qualitymanager'),
        name: 'qualitymanager',
        meta: {
            name: '质量管理',
            icon: 'icon-order-manage'
        },
        children: [
            {
                path: 'dictionarydeploy',
                name: 'dictionarydeploy',
                component: () => import('../pages/qualitymanager/dictionarydeploy'),
                meta: {
                    name: '字典配置',
                    icon: 'icon-home'
                }
            },
            {
                path: 'filesscan',
                name: 'filesscan',
                component: () => import('../pages/qualitymanager/filesscan'),
                meta: {
                    name: '文档扫描',
                    icon: 'icon-product-manage'
                },
                children: [
                    {
                        path: 'singleprojectscan',
                        name: 'singleprojectscan',
                        component: () => import('../pages/qualitymanager/filesscan/singleprojectscan'),
                        meta: {
                            name: '单项目扫描',
                            icon: 'icon-home'
                        }
                    },
                    {
                        path: 'multipleprojectscan',
                        name: 'multipleprojectscan',
                        component: () => import('../pages/qualitymanager/filesscan/multipleprojectscan'),
                        meta: {
                            name: '多项目扫描',
                            icon: 'icon-product-manage'
                        }
                    }
                ]
            }
        ]
    }
];

export default dynamicRoutes
