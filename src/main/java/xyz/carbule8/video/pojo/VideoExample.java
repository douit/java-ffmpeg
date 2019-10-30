package xyz.carbule8.video.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VideoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andVIdIsNull() {
            addCriterion("v_id is null");
            return (Criteria) this;
        }

        public Criteria andVIdIsNotNull() {
            addCriterion("v_id is not null");
            return (Criteria) this;
        }

        public Criteria andVIdEqualTo(String value) {
            addCriterion("v_id =", value, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdNotEqualTo(String value) {
            addCriterion("v_id <>", value, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdGreaterThan(String value) {
            addCriterion("v_id >", value, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdGreaterThanOrEqualTo(String value) {
            addCriterion("v_id >=", value, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdLessThan(String value) {
            addCriterion("v_id <", value, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdLessThanOrEqualTo(String value) {
            addCriterion("v_id <=", value, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdLike(String value) {
            addCriterion("v_id like", value, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdNotLike(String value) {
            addCriterion("v_id not like", value, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdIn(List<String> values) {
            addCriterion("v_id in", values, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdNotIn(List<String> values) {
            addCriterion("v_id not in", values, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdBetween(String value1, String value2) {
            addCriterion("v_id between", value1, value2, "vId");
            return (Criteria) this;
        }

        public Criteria andVIdNotBetween(String value1, String value2) {
            addCriterion("v_id not between", value1, value2, "vId");
            return (Criteria) this;
        }

        public Criteria andVNameIsNull() {
            addCriterion("v_name is null");
            return (Criteria) this;
        }

        public Criteria andVNameIsNotNull() {
            addCriterion("v_name is not null");
            return (Criteria) this;
        }

        public Criteria andVNameEqualTo(String value) {
            addCriterion("v_name =", value, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameNotEqualTo(String value) {
            addCriterion("v_name <>", value, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameGreaterThan(String value) {
            addCriterion("v_name >", value, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameGreaterThanOrEqualTo(String value) {
            addCriterion("v_name >=", value, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameLessThan(String value) {
            addCriterion("v_name <", value, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameLessThanOrEqualTo(String value) {
            addCriterion("v_name <=", value, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameLike(String value) {
            addCriterion("v_name like", value, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameNotLike(String value) {
            addCriterion("v_name not like", value, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameIn(List<String> values) {
            addCriterion("v_name in", values, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameNotIn(List<String> values) {
            addCriterion("v_name not in", values, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameBetween(String value1, String value2) {
            addCriterion("v_name between", value1, value2, "vName");
            return (Criteria) this;
        }

        public Criteria andVNameNotBetween(String value1, String value2) {
            addCriterion("v_name not between", value1, value2, "vName");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNull() {
            addCriterion("upload_time is null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNotNull() {
            addCriterion("upload_time is not null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeEqualTo(Date value) {
            addCriterion("upload_time =", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotEqualTo(Date value) {
            addCriterion("upload_time <>", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThan(Date value) {
            addCriterion("upload_time >", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("upload_time >=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThan(Date value) {
            addCriterion("upload_time <", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThanOrEqualTo(Date value) {
            addCriterion("upload_time <=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIn(List<Date> values) {
            addCriterion("upload_time in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotIn(List<Date> values) {
            addCriterion("upload_time not in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeBetween(Date value1, Date value2) {
            addCriterion("upload_time between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotBetween(Date value1, Date value2) {
            addCriterion("upload_time not between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andVStatusIsNull() {
            addCriterion("v_status is null");
            return (Criteria) this;
        }

        public Criteria andVStatusIsNotNull() {
            addCriterion("v_status is not null");
            return (Criteria) this;
        }

        public Criteria andVStatusEqualTo(String value) {
            addCriterion("v_status =", value, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusNotEqualTo(String value) {
            addCriterion("v_status <>", value, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusGreaterThan(String value) {
            addCriterion("v_status >", value, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusGreaterThanOrEqualTo(String value) {
            addCriterion("v_status >=", value, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusLessThan(String value) {
            addCriterion("v_status <", value, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusLessThanOrEqualTo(String value) {
            addCriterion("v_status <=", value, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusLike(String value) {
            addCriterion("v_status like", value, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusNotLike(String value) {
            addCriterion("v_status not like", value, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusIn(List<String> values) {
            addCriterion("v_status in", values, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusNotIn(List<String> values) {
            addCriterion("v_status not in", values, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusBetween(String value1, String value2) {
            addCriterion("v_status between", value1, value2, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVStatusNotBetween(String value1, String value2) {
            addCriterion("v_status not between", value1, value2, "vStatus");
            return (Criteria) this;
        }

        public Criteria andVSuffixIsNull() {
            addCriterion("v_suffix is null");
            return (Criteria) this;
        }

        public Criteria andVSuffixIsNotNull() {
            addCriterion("v_suffix is not null");
            return (Criteria) this;
        }

        public Criteria andVSuffixEqualTo(String value) {
            addCriterion("v_suffix =", value, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixNotEqualTo(String value) {
            addCriterion("v_suffix <>", value, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixGreaterThan(String value) {
            addCriterion("v_suffix >", value, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixGreaterThanOrEqualTo(String value) {
            addCriterion("v_suffix >=", value, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixLessThan(String value) {
            addCriterion("v_suffix <", value, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixLessThanOrEqualTo(String value) {
            addCriterion("v_suffix <=", value, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixLike(String value) {
            addCriterion("v_suffix like", value, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixNotLike(String value) {
            addCriterion("v_suffix not like", value, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixIn(List<String> values) {
            addCriterion("v_suffix in", values, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixNotIn(List<String> values) {
            addCriterion("v_suffix not in", values, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixBetween(String value1, String value2) {
            addCriterion("v_suffix between", value1, value2, "vSuffix");
            return (Criteria) this;
        }

        public Criteria andVSuffixNotBetween(String value1, String value2) {
            addCriterion("v_suffix not between", value1, value2, "vSuffix");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}