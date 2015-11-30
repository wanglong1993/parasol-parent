package com.ginkgocap.parasol.cache;

public enum CacheModule {
	INC_CARD {
		@Override
		public String getName() {
			return "公司名片";
		}
	},
	INC_CONTENT {
		@Override
		public String getName() {
			return "公司资讯";
		}
	},
	INC_CARD_GROUP {
		@Override
		public String getName() {
			return "公司名片组";
		}
	},
	MEMBER_CENTER {
		@Override
		public String getName() {
			return "会员中心";
		}
	},
	ATTACHMENT {
		@Override
		public String getName() {
			return "附件";
		}
	},
	MSG {
		@Override
		public String getName() {
			return "消息中心";
		}
	},
	FRIEND {
		@Override
		public String getName() {
			return "好友";
		}
	},
	FRIEND_GROUP {
		@Override
		public String getName() {
			return "好友分组";
		}
	},
	REGISTER {
		@Override
		public String getName() {
			return "注册";
		}
	},
	LOGIN {
		@Override
		public String getName() {
			return "登录";
		}
	},
	COMMON {
		@Override
		public String getName() {
			return "通用";
		}
	},
	MONGODB {
		@Override
		public String getName() {
			return "MongoDB";
		}
	},
	CUSTOMER {
		@Override
		public String getName() {
			return "客户";
		}
	},
	CUSTOMER_GROUP {
		@Override
		public String getName() {
			return "客户分组";
		}
	},
	BUSINESSREQUIREMENT {
		@Override
		public String getName() {
			return "业务需求";
		}
	},
	REQUIREMENT {
		@Override
		public String getName() {
			return "需求中心";
		}
	},
	REQUIREMENTCENTER {
		@Override
		public String getName() {
			return "公司需求中心";
		}
	},
	CARD {
		@Override
		public String getName() {
			return "名片";
		}
	},
	CARD_GROUP {
		@Override
		public String getName() {
			return "名片分组";
		}
	},
	CARD_CUSTOM {
		@Override
		public String getName() {
			return "名片自定义字段";
		}
	},
	CARD_IMPORT {
		@Override
		public String getName() {
			return "名片导入";
		}
	},
	CARD_EXPORT {
		@Override
		public String getName() {
			return "名片导出";
		}
	},
	CARD_SHARE {
		@Override
		public String getName() {
			return "名片共享";
		}
	},
	PROJECT {
		@Override
		public String getName() {
			return "公司项目";
		}
	},
	PEOPLE {
		@Override
		public String getName() {
			return "人脉管理";
		}
	},
	PERMISSION {
		@Override
		public String getName() {
			return "权限";
		}
	},
	ROLE {
		@Override
		public String getName() {
			return "角色";
		}
	},
	DEPT {
		@Override
		public String getName() {
			return "部门";
		}
	},
	EMPLOYEE {
		@Override
		public String getName() {
			return "员工";
		}
	},
    REGION {
        @Override
        public String getName() {
            return "地域";
        }
    },
    MSG_DYNAMIC {
        @Override
        public String getName() {
            return "实时消息";
        }
    },
    MSG_LAST_NOTICE {
        @Override
        public String getName() {
            return "最新公告";
        }
    },
    MSG_LAST_LETTER {
        @Override
        public String getName() {
            return "最新私信";
        }
    }
    ,
    MSG_OPERATE_NOTICE {
        @Override
        public String getName() {
            return "操作提示";
        }
    }
    ,
    USER_DYNAMIC_RES {
        @Override
        public String getName() {
            return "用户首页栏目";
        }
    };
	public abstract String getName();

	public int getValue() {
		return ordinal();
	}
}
