package demo.calcite;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/12/1 19:11
 */
public class SqlStrongCheckTestCase {
    static final String SQL_HAS_WITH_INSERT = "with t1 as (\n" +
            "  select a.business_id, date_format(a.end_time, 'yyyy-MM-dd') as dt, sum(a.memory_seconds) as sum_memory\n" +
            "  from table1 a inner join table2 j on a.job_id = j.id and j.job_type != 10\n" +
            "  where a.memory_seconds >= 0 and a.vcore_seconds >= 0 and a.end_time >= '${yesterday} 00:00:00' and a.end_time < '${today} 00:00:00'\n" +
            "  group by a.business_id,date_format(a.end_time, 'yyyy-MM-dd')\n" +
            "),\n" +
            "t2 as (\n" +
            "  select a.business_id,date_format(a.end_time, 'yyyy-MM-dd') as dt,sum(a.vcore_seconds) as sum_vcore\n" +
            "  from table1 a inner join table2 j on a.job_id = j.id and j.job_type != 10\n" +
            "  where a.memory_seconds >= 0 and a.vcore_seconds >= 0 and a.end_time >= '${yesterday} 00:00:00' and a.end_time < '${today} 00:00:00'\n" +
            "  group by a.business_id, date_format(a.end_time, 'yyyy-MM-dd')\n" +
            "),\n" +
            "t3 as (\n" +
            "  select business_id,dt, sum_memory/(3600*1024) as vvv, 'ares_job_mem' as metric from t1\n" +
            "  union all\n" +
            "  select business_id, dt, sum_vcore/3600 as vvv, 'ares_job_cpu' as metric from t2\n" +
            ")\n" +
            "insert overwrite table test partition (p_date = '${yesterday}')\n" +
            "select business_id, metric, vvv, dt from t3";
    static final String SQL_ONLY_INSER = "insert overwrite table table2_detail partition(p_date = '${today}')\n" +
            "select\n" +
            "    j.id, j.job_name, j.job_name_cn, j.business_id, j.sub_business_id, j.link_url, j.job_type, \n" +
            "    j.priority, j.script_location, j.execute_account, j.execute_queue, j.launch_command, j.`parameter`, \n" +
            "    j.env_vars, j.output_period, j.`trigger`, j.timeout, j.concurrency, j.is_sla, j.alarm_strategy, \n" +
            "    j.creator, j.demander, j.retry_count, j.retry_interval, j.description, j.`status`, j.create_time, \n" +
            "    j.update_time, temp.avg_running_duration as running_time\n" +
            "from table2 j left join table2_running_duration temp on j.id = temp.job_id and temp.p_date = '${today}'";
    static final String SQL_ONLY_SELECT = "select business_id, metric, vvv, dt from t3";
    static final String SQL_REMARK_INSERT = "--insert overwrite table table2_detail partition(p_date = '${today}')\n" +
            "select\n" +
            "    j.id, j.job_name, j.job_name_cn, j.business_id, j.sub_business_id, j.link_url, j.job_type, \n" +
            "    j.priority, j.script_location, j.execute_account, j.execute_queue, j.launch_command, j.`parameter`, \n" +
            "    j.env_vars, j.output_period, j.`trigger`, j.timeout, j.concurrency, j.is_sla, j.alarm_strategy, \n" +
            "    j.creator, j.demander, j.retry_count, j.retry_interval, j.description, j.`status`, j.create_time, \n" +
            "    j.update_time, temp.avg_running_duration as running_time\n" +
            "from table2 j left join table2_running_duration temp on j.id = temp.job_id and temp.p_date = '${today}'";
    static final String SQL_MULTIPLE_SELECT = "select business_id, metric, vvv, dt from t3;\n" +
            "select business_id, metric, vvv, dt from t3;";

    static final String SQL_MULTIPLE_SELECT_HAS_INSERT = "select business_id, metric, vvv, dt from t3;\n" +
            "insert overwrite table table1_summary partition (p_date = '${yesterday}') select business_id, metric, vvv, dt from t3;\n" +
            "select business_id, metric, vvv, dt from t3;";

    static final String SQL_ONLY_CREATE_VIEW = "create or replace view  ods_action_answer.ods_answer_collect_pt_info(\n" +
            "  `member_id`            COMMENT '动作执行人编号 member_id',\n" +
            "  `content_id`           COMMENT '内容编号 这里是回答编号',\n" +
            "  `status`               COMMENT '所属收藏夹状态',\n" +
            "  `action_time`          COMMENT '动作发生时间 时间戳',\n" +
            "  `action_date`          COMMENT '动作发生日期 yyyy-MM-dd',\n" +
            "  `p_year`   COMMENT '分区字段: yyyy',\n" +
            "  `p_month`  COMMENT '分区字段: MM',\n" +
            "  `p_day`    COMMENT '分区字段: dd'\n" +
            ") COMMENT '回答收藏基础元信息表(ods_content.ods_content_answer_favlist_content_pt p_year视图表)'\n" +
            "PARTITIONED ON (p_year,p_month,p_day)\n" +
            "as\n" +
            "select\n" +
            "`member_id`,\n" +
            "`content_id`,\n" +
            "`status`,\n" +
            "`action_time`,\n" +
            "`action_date`,\n" +
            "substr(p_date,1,4) as p_year,\n" +
            "substr(p_date,6,2) as p_month,\n" +
            "substr(p_date,9,2) as p_day\n" +
            "FROM  ods_content.ods_content_answer_favlist_content_pt";

    static final String SQL_MULTIPLE_CREATE_INSERT = "create TEMPORARY table ads_drama.ads_drama_channel_tmp as\n" +
            "select channel_name                   -- 渠道名称\n" +
            "      ,coalesce(is_first_watch_drama, 99) as is_first_watch_drama -- 是否首次观看直播\n" +
            "      ,grouping__id grouping_id\n" +
            "      ,sum(cardshow_pv) as cardshow_pv                    -- 卡片曝光pv\n" +
            "      ,count(case when cardshow_pv>0 then client_id else null end) as cardshow_uv                    -- 卡片曝光uv\n" +
            "      ,count(case when cardshow_pv>0 then drama_id else null end) as cardshow_drama_num             -- 卡片曝光直播数量\n" +
            "      ,sum(cardclick_pv) as cardclick_pv                   -- 卡片点击pv\n" +
            "      ,count(case when cardclick_pv>0 then client_id else null end) as cardclick_uv                   -- 卡片点击uv\n" +
            "      ,count(case when cardclick_pv>0 then drama_id else null end) as cardclick_drama_num            -- 卡片点击直播数量\n" +
            "      ,sum(direct_watch_pv) as direct_watch_pv                -- 直接观看pv\n" +
            "      ,sum(watch_pv) as watch_pv                       -- 观看pv\n" +
            "      ,count(distinct case when watch_pv>0 then client_id else null end ) as watch_uv                       -- 观看uv\n" +
            "      ,count(distinct case when valid_watch_duration>0 then client_id else null end ) valid_watch_uv                 -- 有效观看uv\n" +
            "      ,sum(watch_duration) as watch_duration                 -- 观看时长\n" +
            "      ,sum(valid_watch_duration) as valid_watch_duration           -- 有效观看时长\n" +
            "      ,count(distinct case when watch_pv>0 then concat(drama_id,'|',client_id) else null end) as watch_user_num                 -- 观看人数\n" +
            "      ,count(distinct case when watch_pv>0 then drama_id else null end) as watch_drama_num                -- 观看直播数量\n" +
            "      ,sum(total_interactive_pv) as total_interactive_pv           -- 总互动pv\n" +
            "      ,count(distinct case when total_interactive_pv>0 then client_id else null end) as total_interactive_uv           -- 总互动uv\n" +
            "      ,sum(valid_total_interactive_pv) as valid_total_interactive_pv     -- 有效总互动pv\n" +
            "      ,count(distinct case when valid_total_interactive_pv>0 then client_id else null end) as valid_total_interactive_uv     -- 有效总互动uv\n" +
            "      ,sum(pay_interactive_pv) as pay_interactive_pv             -- 付费互动pv\n" +
            "      ,count(distinct case when pay_interactive_pv>0 then client_id else null end) as pay_interactive_uv             -- 付费互动uv\n" +
            "      ,sum(free_interactive_pv) as free_interactive_pv             -- 免费互动pv\n" +
            "      ,count(distinct case when free_interactive_pv>0 then client_id else null end) as free_interactive_uv             -- 免费互动uv\n" +
            "      ,sum(pay_salt_num) as pay_salt_num                   -- 付费盐粒数\n" +
            "      ,sum(bullet_pv) as bullet_pv                  -- 弹幕PV\n" +
            "      ,count(distinct case when bullet_pv>0 then client_id else null end) as bullet_uv\n" +
            "      ,sum(case when is_valid_watch = 1 then bullet_pv else 0 end) as valid_bullet_pv\n" +
            "      ,count(distinct case when is_valid_watch = 1 and bullet_pv>0 then client_id else null end) as valid_bullet_uv\n" +
            "      ,sum(question_pv) as question_pv                  -- 提问PV\n" +
            "      ,count(distinct case when question_pv>0 then client_id else null end) as question_uv\n" +
            "      ,sum(case when is_valid_watch = 1 then question_pv else 0 end) as valid_question_pv\n" +
            "      ,count(distinct case when is_valid_watch = 1 and question_pv>0 then client_id else null end) as valid_question_uv\n" +
            "      ,sum(point_pv) as point_pv                  -- 观点PV\n" +
            "      ,count(distinct case when point_pv>0 then client_id else null end) as point_uv\n" +
            "      ,sum(case when is_valid_watch = 1 then point_pv else 0 end) as valid_point_pv\n" +
            "      ,count(distinct case when is_valid_watch = 1 and point_pv>0 then client_id else null end) as valid_point_uv\n" +
            "      ,sum(follow_pv) as follow_pv                  -- 观点PV\n" +
            "      ,count(distinct case when follow_pv>0 then client_id else null end) as follow_uv\n" +
            "      ,sum(case when is_valid_watch = 1 then follow_pv else 0 end) as valid_follow_pv\n" +
            "      ,count(distinct case when is_valid_watch = 1 and follow_pv>0 then client_id else null end) as valid_follow_uv\n" +
            "      ,sum(like_pv) as like_pv                  -- 轻互动PV\n" +
            "      ,count(distinct case when like_pv>0 then client_id else null end) as like_uv\n" +
            "      ,sum(case when is_valid_watch = 1 then like_pv else 0 end) as valid_like_pv\n" +
            "      ,count(distinct case when is_valid_watch = 1 and like_pv>0 then client_id else null end) as valid_like_uv\n" +
            "      ,sum(gift_pv) as gift_pv                  -- 免费送礼PV\n" +
            "      ,count(distinct case when gift_pv>0 then client_id else null end) as gift_uv\n" +
            "      ,sum(case when is_valid_watch = 1 then gift_pv else 0 end) as valid_gift_pv\n" +
            "      ,count(distinct case when is_valid_watch = 1 and gift_pv>0 then client_id else null end) as valid_gift_uv\n" +
            "      ,sum(free_gift_pv) as free_gift_pv                  -- 免费送礼PV\n" +
            "      ,count(distinct case when free_gift_pv>0 then client_id else null end) as free_gift_uv\n" +
            "      ,sum(case when is_valid_watch = 1 then free_gift_pv else 0 end) as valid_free_gift_pv\n" +
            "      ,count(distinct case when is_valid_watch = 1 and free_gift_pv>0 then client_id else null end) as valid_free_gift_uv\n" +
            "      ,sum(pay_gift_pv) as pay_gift_pv                  -- 付费送礼PV\n" +
            "      ,count(distinct case when pay_gift_pv>0 then client_id else null end) as pay_gift_uv\n" +
            "      ,sum(case when is_valid_watch = 1 then pay_gift_pv else 0 end) as valid_pay_gift_pv\n" +
            "      ,count(distinct case when is_valid_watch = 1 and pay_gift_pv>0 then client_id else null end) as valid_pay_gift_uv\n" +
            " from dws_content.dws_content_drama_channel_wide_pd\n" +
            " where p_date = '${yesterday}'\n" +
            " group by channel_name,is_first_watch_drama\n" +
            " grouping sets( (channel_name), (channel_name,is_first_watch_drama))\n" +
            ";\n" +
            "create TEMPORARY table ads_drama.ads_drama_drama_tmp as\n" +
            "select channel_name                   -- 渠道名称s\n" +
            "      ,coalesce(is_first_watch_drama, 99) as is_first_watch_drama -- 是否首次观看直播\n" +
            "      ,grouping__id grouping_id\n" +
            "      ,round(avg(bullet_uv/watch_uv),4)       as drama_avg_bullet_rate\n" +
            "      ,round(avg(bullet_uv/valid_watch_uv),4) as drama_avg_valid_bullet_rate\n" +
            "      ,round(avg(pay_gift_uv/watch_uv),4)     as drama_avg_pay_gift_rate\n" +
            "      ,round(avg(like_uv/watch_uv),4)         as drama_avg_like_rate\n" +
            "      ,round(avg(question_uv/watch_uv),4)     as drama_avg_question_rate\n" +
            "      ,round(avg(question_uv),4)              as drama_avg_question_uv\n" +
            "  from (select channel_name\n" +
            "              ,is_first_watch_drama\n" +
            "              ,drama_id\n" +
            "              ,count(distinct case when watch_pv>0 then client_id else null end ) as watch_uv\n" +
            "              ,count(distinct case when valid_watch_duration>0 then client_id else null end ) as valid_watch_uv\n" +
            "              ,count(distinct case when bullet_pv>0 then client_id else null end) as bullet_uv\n" +
            "              ,count(distinct case when is_valid_watch = 1 and bullet_pv>0 then client_id else null end) as valid_bullet_uv\n" +
            "              ,count(distinct case when pay_gift_pv>0 then client_id else null end) as  pay_gift_uv\n" +
            "              ,count(distinct case when like_pv>0 then client_id else null end) as like_uv\n" +
            "              ,count(distinct case when question_pv>0 then client_id else null end) as question_uv\n" +
            "          from dws_content.dws_content_drama_channel_wide_pd\n" +
            "         where p_date = '${yesterday}'\n" +
            "         group by channel_name\n" +
            "                 ,is_first_watch_drama\n" +
            "                 ,drama_id\n" +
            "        ) m\n" +
            " group by channel_name ,is_first_watch_drama\n" +
            " grouping sets( (channel_name), (channel_name,is_first_watch_drama))\n" +
            ";\n" +
            "\n" +
            "insert overwrite table ads_drama.ads_drama_channel_index_pd partition (p_date = '${yesterday}')\n" +
            "select t1.channel_name\n" +
            "      ,t1.is_first_watch_drama\n" +
            "      ,t1.cardshow_pv\n" +
            "      ,t1.cardshow_uv\n" +
            "      ,t1.cardshow_drama_num\n" +
            "      ,t1.cardclick_pv\n" +
            "      ,t1.cardclick_uv\n" +
            "      ,t1.cardclick_drama_num\n" +
            "      ,t1.direct_watch_pv\n" +
            "      ,t1.watch_pv\n" +
            "      ,t1.watch_uv\n" +
            "      ,t1.valid_watch_uv\n" +
            "      ,t1.watch_duration\n" +
            "      ,t1.valid_watch_duration\n" +
            "      ,t1.watch_user_num\n" +
            "      ,t1.watch_drama_num\n" +
            "      ,t1.total_interactive_pv\n" +
            "      ,t1.total_interactive_uv\n" +
            "      ,t1.valid_total_interactive_pv\n" +
            "      ,t1.valid_total_interactive_uv\n" +
            "      ,t1.pay_interactive_pv\n" +
            "      ,t1.pay_interactive_uv\n" +
            "      ,t1.pay_salt_num\n" +
            "      ,t1.bullet_pv\n" +
            "      ,t1.bullet_uv\n" +
            "      ,t1.valid_bullet_pv\n" +
            "      ,t1.valid_bullet_uv\n" +
            "      ,t1.question_pv\n" +
            "      ,t1.question_uv\n" +
            "      ,t1.valid_question_pv\n" +
            "      ,t1.valid_question_uv\n" +
            "      ,t1.point_pv\n" +
            "      ,t1.point_uv\n" +
            "      ,t1.valid_point_pv\n" +
            "      ,t1.valid_point_uv\n" +
            "      ,t1.follow_pv\n" +
            "      ,t1.follow_uv\n" +
            "      ,t1.valid_follow_pv\n" +
            "      ,t1.valid_follow_uv\n" +
            "      ,t1.like_pv\n" +
            "      ,t1.like_uv\n" +
            "      ,t1.valid_like_pv\n" +
            "      ,t1.valid_like_uv\n" +
            "      ,t1.gift_pv\n" +
            "      ,t1.gift_uv\n" +
            "      ,t1.free_gift_pv\n" +
            "      ,t1.free_gift_uv\n" +
            "      ,t1.pay_gift_pv\n" +
            "      ,t1.pay_gift_uv\n" +
            "      ,t2.drama_avg_bullet_rate\n" +
            "      ,t2.drama_avg_valid_bullet_rate\n" +
            "      ,t2.drama_avg_pay_gift_rate\n" +
            "      ,t2.drama_avg_like_rate\n" +
            "      ,t2.drama_avg_question_rate\n" +
            "      ,t2.drama_avg_question_uv\n" +
            "  from ads_drama.ads_drama_channel_tmp t1\n" +
            "  left join ads_drama.ads_drama_drama_tmp t2\n" +
            "    on t1.channel_name = t2.channel_name\n" +
            "   and t1.is_first_watch_drama = t2.is_first_watch_drama\n" +
            ";";

    // https://data.in.zhihu.com/develop/scheduling/aresapp#/job/994/detail
    static final String SQL_BADCASE_1 = "-- http://dd.in.zhihu.com/aresapp/#/job/994/detail\n" +
            "\n" +
            "-- S1：帐号分 --\n" +
            "-- 对应用户视角的「基础信用」维度分\n" +
            "-- 计算用户的「基本资料填写情况」「帐户挂起、锁定、封禁等状态」「用户是否是 PU 粉丝数等正向数据」输出一个帐号的分数\n" +
            "-- 基本完整度取值 0.3 - 0.7（有手机号码则 0.1，是否认证 0.1，头像，性别，一句话介绍，（居住地，行业，职业经历，教育经历）学校，职业，个人简介 一起组成0.2）\n" +
            "-- 负向 0 - 0.3 （hang：0.3，lock：0.2，ban：0.1）\n" +
            "-- 正向 0.7 - 1 （PU：0.1，优秀：0.1, 粉丝数拟合：0.1）\n" +
            "-- delete jars;\n" +
            "-- delete files;\n" +
            "ADD FILE hdfs:///user/cc_operation/user/luosiqi/utils/transform_base_fans_r.py;\n" +
            "-- set hive.execution.engine=mr;\n" +
            "set mapreduce.map.memory.mb=8192;\n" +
            "set mapreduce.reduce.memory.mb=8192;\n" +
            "\n" +
            "INSERT OVERWRITE TABLE user_credit.s1_profile_final\n" +
            "PARTITION (dt = '${yesterday}')\n" +
            "SELECT\n" +
            "    m.member_id as member_id,\n" +
            "    0.3\n" +
            "    + phone_register + coalesce(is_verified, 0)\n" +
            "    + coalesce(level, 0) + coalesce(is_best_answer, 0) + coalesce(0.1 * fan, 0)\n" +
            "    + avatar_path + gender + headline + description\n" +
            "    + coalesce(profession, 0) + coalesce(school, 0) + coalesce(industry, 0) + coalesce(location, 0)\n" +
            "    + coalesce(penalty, 0)\n" +
            "    as score,\n" +
            "    phone_register, is_verified,\n" +
            "    level, is_best_answer, fan,\n" +
            "    avatar_path, gender, headline, description, profession, school,\n" +
            "    penalty\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "    -- 子查询用户基本资料信息，用作统计\n" +
            "    -- 通过 zhihu_account.accout 表，查询用户帐号的基本资料完整度，计算得分\n" +
            "    -- 0.3 - 0.7（有手机号码则 0.1，是否认证 0.1，头像，性别，一句话介绍，（居住地，行业，职业经历，教育经历）学校，职业，个人简介 一起组成0.2）\n" +
            "        old_id AS member_id,\n" +
            "        if(phone_no is null, 0, 1) * 0.1 as phone_register,\n" +
            "        if(avatar_path is null, 0, 1) * (0.2 / 8) as avatar_path,\n" +
            "        if(gender is null, 0, 1) * (0.2 / 8) as gender,\n" +
            "        if(headline is null, 0, 1) * (0.2 / 8) as headline,\n" +
            "        if(description='', 0, 1) * (0.2 / 8) as description\n" +
            "    FROM zhihu_account.account\n" +
            "    WHERE from_unixtime(created_at) < '${today}'\n" +
            ") m                                         -- 基本信息分\n" +
            "LEFT JOIN (\n" +
            "    -- 子查询用户的负向记录分，用作统计\n" +
            "    SELECT\n" +
            "      member_id,\n" +
            "      if(sum(coalesce(penalty, 0)) = 4, 3, sum(coalesce(penalty, 0))) * (-0.3 / 3) as penalty\n" +
            "    FROM(\n" +
            "        SELECT\n" +
            "        -- 通过 account_status.account_activitiy 表，查询用户帐号的异常状态\n" +
            "          member_id,\n" +
            "          (if(count(if((operate_type = 1) AND (status = 0), status, null)) - count(if((operate_type = 2) AND (status = 0), status, null)) > 0, 1, 0)\n" +
            "           +if(count(if((operate_type = 1) AND (status = 1), status, null)) - count(if((operate_type = 2) AND (status = 1), status, null)) > 0, 1, 0)\n" +
            "           +if(count(if((operate_type = 1) AND (status = 2), status, null)) - count(if((operate_type = 2) AND (status = 2), status, null)) > 0, 1, 0)\n" +
            "          )\n" +
            "          as penalty\n" +
            "        FROM account_status.account_activity  -- operate_type = 1 为发起 2 为恢复\n" +
            "        WHERE status in (0, 1, 2)\n" +
            "          AND from_unixtime(created) < '${today}'\n" +
            "        GROUP BY member_id\n" +
            "        union all\n" +
            "        SELECT\n" +
            "          distinct member_id,\n" +
            "          1 as penalty\n" +
            "        FROM zhihu_ticket.ticket_log_kafka\n" +
            "        WHERE reason_id = 31001\n" +
            "          AND `action` in (3006, 5013)\n" +
            "          and p_date <= '${yesterday}'\n" +
            "          AND from_unixtime(created_at) >= '2018-07-27'\n" +
            "    ) tmp\n" +
            "    group by\n" +
            "      member_id\n" +
            ") bad                                        -- 负向减分项\n" +
            "ON bad.member_id = m.member_id\n" +
            "LEFT JOIN (\n" +
            "    -- 子查询用户的正向记录分，用作统计\n" +
            "    SELECT\n" +
            "        m.member_id as member_id,\n" +
            "        if(is_best_answer = 1, 0.1, 0) as is_best_answer,\n" +
            "        if(is_verify = 1, 0.1, 0) as is_verified,\n" +
            "        if(profession is null, 0, (0.2 / 8)) as profession,\n" +
            "        if(school is null, 0, (0.2 / 8)) as school,\n" +
            "        if(industry is null, 0, (0.2 / 8)) as industry,\n" +
            "        if(location is null, 0, (0.2 / 8)) as location,\n" +
            "        if(level >= 6,0.1,0) as level\n" +
            "    FROM (\n" +
            "        SELECT\n" +
            "            member_id,\n" +
            "            is_pu,\n" +
            "            is_best_answer\n" +
            "        FROM dw_member.dw_member_pt_info\n" +
            "        WHERE concat_ws('-', p_year, p_month, p_day) = '${yesterday}'\n" +
            "    ) m\n" +
            "    LEFT JOIN (\n" +
            "        SELECT\n" +
            "            distinct\n" +
            "            member_id,\n" +
            "            1 as location\n" +
            "        FROM zhihu_profiled.location\n" +
            "    ) v\n" +
            "    ON v.member_id = m.member_id\n" +
            "    LEFT JOIN (\n" +
            "        SELECT\n" +
            "            distinct\n" +
            "            member_id,\n" +
            "            1 as industry\n" +
            "        FROM zhihu_profiled.member_business\n" +
            "    ) w\n" +
            "    ON w.member_id = m.member_id\n" +
            "    LEFT JOIN (\n" +
            "        SELECT\n" +
            "            distinct\n" +
            "            member_id,\n" +
            "            1 as school\n" +
            "        FROM zhihu_profiled.education\n" +
            "    ) x\n" +
            "    ON x.member_id = m.member_id\n" +
            "    LEFT JOIN (\n" +
            "        SELECT\n" +
            "            distinct\n" +
            "            member_id,\n" +
            "            1 as profession\n" +
            "        FROM zhihu_profiled.employment\n" +
            "    ) y\n" +
            "    ON y.member_id = m.member_id\n" +
            "    LEFT JOIN (\n" +
            "        -- 子查询用户认证情况\n" +
            "        SELECT\n" +
            "            distinct\n" +
            "            member_id,\n" +
            "            1 as is_verify\n" +
            "        FROM qualificationdb.member_verify\n" +
            "        WHERE deleted = 0\n" +
            "          AND audit_status = 1\n" +
            "        UNION\n" +
            "        SELECT\n" +
            "            distinct\n" +
            "            id as member_id,\n" +
            "            1 as is_verify\n" +
            "        FROM zhihu_account.org\n" +
            "        WHERE verify_status = 1\n" +
            "    ) z\n" +
            "    ON z.member_id = m.member_id\n" +
            "    LEFT JOIN(\n" +
            "        SELECT\n" +
            "          -- 子查询用户的创作者中心用户等级\n" +
            "          -- 创作分是盐值计算的重要分支，创作者中心用户等级\n" +
            "          -- 优先使用 creator 的数据，如果无，再使用 ods_creator_tool_creator_adjusted_score_pt\n" +
            "          -- http://wiki.in.zhihu.com/pages/viewpage.action?pageId=99825353\n" +
            "          ods_creator_tool_creator_adjusted_score_pt.member_id,\n" +
            "          if(creator.level is not null, creator.level, ods_creator_tool_creator_adjusted_score_pt.level) as level\n" +
            "        FROM\n" +
            "          ods_member.ods_creator_tool_creator_adjusted_score_pt\n" +
            "          LEFT JOIN creator.creator on ods_creator_tool_creator_adjusted_score_pt.member_id = cast(creator.author_id as string)\n" +
            "        WHERE p_date = '${yesterday}'\n" +
            "            )l\n" +
            "    on l.member_id = m.member_id\n" +
            ") good                                    -- 正向加分项目\n" +
            "ON good.member_id = m.member_id\n" +
            "LEFT JOIN (\n" +
            "-- 子查询用户的粉丝或关注数加分\n" +
            "-- 原先误将粉丝数用为关注数，修正方法中采用 max(粉丝数 , 关注数) 替代\n" +
            "    SELECT\n" +
            "        transform(x.member_id, c) using 'python transform_base_fans_r.py' as (member_id, fan)\n" +
            "    FROM (\n" +
            "        SELECT\n" +
            "          account.member_id,\n" +
            "          if(\n" +
            "            coalesce(follower.c, 0) > coalesce(followee.c, 0),\n" +
            "            coalesce(follower.c, 0),\n" +
            "            coalesce(followee.c, 0)\n" +
            "          ) AS c\n" +
            "        FROM\n" +
            "          (\n" +
            "            SELECT\n" +
            "              DISTINCT member_id\n" +
            "            FROM\n" +
            "              (\n" +
            "                SELECT\n" +
            "                  member_id\n" +
            "                FROM\n" +
            "                  zhihu.member_followed_member\n" +
            "                UNION ALL\n" +
            "                SELECT\n" +
            "                  followed_member_id as member_id\n" +
            "                FROM\n" +
            "                  zhihu.member_followed_member\n" +
            "              ) raw_account\n" +
            "          ) account\n" +
            "          LEFT JOIN (\n" +
            "            SELECT\n" +
            "              member_id,\n" +
            "              count(DISTINCT followed_member_id) AS c\n" +
            "            FROM\n" +
            "              zhihu.member_followed_member\n" +
            "            WHERE\n" +
            "              from_unixtime(created) < '${today}'\n" +
            "            GROUP BY\n" +
            "              member_id\n" +
            "          ) follower on account.member_id = follower.member_id\n" +
            "          LEFT JOIN (\n" +
            "            SELECT\n" +
            "              followed_member_id,\n" +
            "              count(DISTINCT member_id) AS c\n" +
            "            FROM\n" +
            "              zhihu.member_followed_member\n" +
            "            WHERE\n" +
            "              from_unixtime(created) < '${today}'\n" +
            "            GROUP BY\n" +
            "              followed_member_id\n" +
            "          ) followee ON account.member_id = followee.followed_member_id\n" +
            "    ) x\n" +
            ") fan                                  -- 粉丝或关注数加分项\n" +
            "ON fan.member_id = m.member_id;";

    // https://data.in.zhihu.com/develop/scheduling/aresapp#/job/1060/detail
    static final String SQL_BADCASE_2 = "-- t4：违反公约分 --\n" +
            "-- 由 s5 ~ s9 组合而成，综合衡量用户在负向维度的得分\n" +
            "-- 放大最差项占比，防止用户可以在其他维度拉高分\n" +
            "-- delete jars;\n" +
            "-- delete files;\n" +
            "ADD FILE hdfs:///user/cc_operation/user/luosiqi/utils/weight_sum.py;\n" +
            "-- set hive.execution.engine=mr;\n" +
            "\n" +
            "set mapreduce.map.memory.mb=4096;\n" +
            "set mapreduce.reduce.memory.mb=4096;\n" +
            "\n" +
            "INSERT OVERWRITE TABLE user_credit.t4_law_abiding\n" +
            "PARTITION (dt = '${yesterday}')\n" +
            "SELECT\n" +
            "x.member_id as member_id ,\n" +
            "if(y.member_id is null, x.score, 0) as score\n" +
            "FROM (\n" +
            "SELECT\n" +
            "transform(s7.score, s8.score, s9.score, s11.score, s12.score, 0.5, 0.2, 0.1, 0.1, 0.1, s7.member_id) using 'python weight_sum.py' as (member_id, score)\n" +
            "from user_credit.s7_wukong_final s7\n" +
            "join user_credit.s8_walle_final s8\n" +
            "on s8.member_id = s7.member_id AND s8.dt = '${yesterday}'\n" +
            "join user_credit.s9_be_reported_final s9\n" +
            "on s9.member_id = s7.member_id AND s9.dt = '${yesterday}'\n" +
            "join user_credit.s11_security_policy_final s11\n" +
            "on s11.member_id = s7.member_id AND s11.dt = '${yesterday}'\n" +
            "join user_credit.s12_atmosphere_strategy_final s12\n" +
            "on s12.member_id = s7.member_id AND s12.dt = '${yesterday}'\n" +
            "WHERE s7.dt = '${yesterday}'\n" +
            ") x\n" +
            "left join (\n" +
            "SELECT\n" +
            "DISTINCT member_id\n" +
            "FROM account_status.account_status\n" +
            "WHERE status=2 and expiration>=86400000 and from_unixtime(created) < '${today}'\n" +
            ") y\n" +
            "on y.member_id = x.member_id";

    // https://data.in.zhihu.com/develop/scheduling/aresapp#/job/1760/detail
    static final String SQL_BADCASE_3 = "select t5.user_id as user_id,\n" +
            "       t5.tot_roll_in as tot_roll_in,\n" +
            "       t5.tot_roll_in_refund as tot_roll_in_refund,\n" +
            "       t5.tot_consume as tot_consume,\n" +
            "       t5.sys_compensate as sys_compensate,\n" +
            "       t5.balance_start as balance_start,\n" +
            "       t5.balance_end as balance_end,\n" +
            "       t5.tot_roll_in - t5.tot_roll_in_refund - t5.tot_consume + t5.sys_compensate as balance_should,\n" +
            "       t5.balance_end - (t5.tot_roll_in - t5.tot_roll_in_refund - t5.tot_consume + t5.sys_compensate) as balance_diff\n" +
            "  from\n" +
            "(select t2.user_id,\n" +
            "       t2.tot_roll_in,\n" +
            "       t2.tot_roll_in_refund,\n" +
            "       t2.tot_consume,\n" +
            "       t2.sys_compensate,\n" +
            "       t3.balance balance_start,\n" +
            "       t4.balance balance_end\n" +
            "  from\n" +
            "(select t1.user_id user_id,\n" +
            "       sum(t1.roll_in) tot_roll_in,\n" +
            "       sum(t1.roll_in_refund) tot_roll_in_refund,\n" +
            "       sum(t1.consume) tot_consume,\n" +
            "       sum(t1.sys_compensate) sys_compensate,\n" +
            "       min(t1.id) min_id,\n" +
            "       max(t1.id) max_id\n" +
            "  from\n" +
            "  (select a.id as id,\n" +
            "          a.balance as balance,\n" +
            "      CASE a.type\n" +
            "        WHEN 1 then a.debit_user_id\n" +
            "        WHEN 2 then a.credit_user_id\n" +
            "        WHEN 3 then a.credit_user_id\n" +
            "        when 5 then a.debit_user_id\n" +
            "      ELSE 0 end as user_id,\n" +
            "          a.type as type,\n" +
            "      CASE a.type\n" +
            "        WHEN 1 then a.amount\n" +
            "      ELSE 0 end as roll_in,\n" +
            "      CASE a.type\n" +
            "        WHEN 2 then a.amount\n" +
            "      ELSE 0 end as consume,\n" +
            "      CASE a.type\n" +
            "        WHEN 3 then a.amount\n" +
            "      ELSE 0 end as roll_in_refund,\n" +
            "      CASE a.type\n" +
            "        WHEN 5 then a.amount\n" +
            "      ELSE 0 end as sys_compensate\n" +
            "from bidding_account.transaction_t a\n" +
            "where a.created >= '2017-06-01' and a.created < '${yesterday}') t1, `plutus.user` u1\n" +
            "where t1.user_id = u1.id and u1.authority = 1 and t1.user_id is not null group by t1.user_id) t2, bidding_account.transaction_t t3, bidding_account.transaction_t t4\n" +
            "where t2.min_id = t3.id and t2.max_id = t4.id) t5\n" +
            "order by balance_diff desc limit 100000;";

    static final String SQL_CREATETABLE = "CREATE EXTERNAL TABLE `dw_member.dw_member_pt_info`(\n" +
            "  `member_id` bigint COMMENT '用户的 id', \n" +
            "  `hash_id` string COMMENT '用户的 hash id', \n" +
            "  `email` string COMMENT '用户的邮箱', \n" +
            "  `phone_no` string COMMENT '用户的手机号', \n" +
            "  `url_token` string COMMENT '用户的 url_token', \n" +
            "  `fullname` string COMMENT '用户的全名', \n" +
            "  `gender` string COMMENT '用户的性别', \n" +
            "  `headline` string COMMENT '用户的签名', \n" +
            "  `description` string COMMENT '用户的自我描述', \n" +
            "  `user_type` tinyint COMMENT '用户类型: 0 普通用户 1 机构账号 2 游客', \n" +
            "  `is_active` bigint COMMENT '用户是否激活', \n" +
            "  `is_pu` tinyint COMMENT '是否是 pu 用户', \n" +
            "  `is_best_answer` tinyint COMMENT '是否是优秀回答者', \n" +
            "  `frequency` string COMMENT '访问频率', \n" +
            "  `loyalty` string COMMENT '注册天数', \n" +
            "  `people_rank` bigint COMMENT '用户排名', \n" +
            "  `follower_num` int COMMENT '粉丝数', \n" +
            "  `followee_num` int COMMENT '关注用户数', \n" +
            "  `follow_topic_num` int COMMENT '关注话题数', \n" +
            "  `follow_question_num` int COMMENT '关注问题数', \n" +
            "  `answer_num` int COMMENT '答案数', \n" +
            "  `question_num` int COMMENT '问题数', \n" +
            "  `article_num` int COMMENT '文章数', \n" +
            "  `comment_num` int COMMENT '评论数', \n" +
            "  `hold_live_num` int COMMENT '主讲 Live 场次', \n" +
            "  `pin_num` int COMMENT '发 pin 的数量', \n" +
            "  `public_fav_num` int COMMENT '创建的公开收藏夹数', \n" +
            "  `answer_voted_num` int COMMENT '答案获得的点赞总数', \n" +
            "  `article_voted_num` int COMMENT '文章获得的点赞总数', \n" +
            "  `pin_voted_num` int COMMENT 'pin 获得的点赞总数', \n" +
            "  `answer_faved_num` int COMMENT '答案被收藏总数', \n" +
            "  `article_faved_num` int COMMENT '文章被收藏总数', \n" +
            "  `answer_commented_num` int COMMENT '答案被评论总数', \n" +
            "  `article_commented_num` int COMMENT '文章被评论总数', \n" +
            "  `vote_answer_num` int COMMENT '对答案点赞的总数', \n" +
            "  `down_vote_answer_num` int COMMENT '对答案点反对的总数', \n" +
            "  `thank_answer_num` int COMMENT '对答案感谢的总数', \n" +
            "  `vote_pin_num` int COMMENT '对 pin 点赞的总数', \n" +
            "  `invites` int COMMENT '邀请别人回答次数', \n" +
            "  `live_order_num` int COMMENT '购买 live 数', \n" +
            "  `book_order_num` int COMMENT '购买电子书数', \n" +
            "  `article_order_num` int COMMENT '赞赏文章数', \n" +
            "  `live_amount` int COMMENT '购买 live 总钱数', \n" +
            "  `book_amount` int COMMENT '购买电子书总钱数', \n" +
            "  `article_amount` int COMMENT '赞赏文章的总钱数', \n" +
            "  `reg_source` tinyint COMMENT '用户注册来源', \n" +
            "  `reg_type` tinyint COMMENT '用户注册类型', \n" +
            "  `modified_at` bigint COMMENT '用户修改时间', \n" +
            "  `modified_date` string COMMENT '用户修改时间: yyyy-MM-dd', \n" +
            "  `created_at` bigint COMMENT '用户的注册时间', \n" +
            "  `created_date` string COMMENT '用户的注册时间: yyyy-MM-dd', \n" +
            "  `marked_answer_num` int COMMENT '被标优的答案数', \n" +
            "  `marked_article_num` int COMMENT '被标优的文章数', \n" +
            "  `medal_id_info` array<bigint> COMMENT '徽章id信息', \n" +
            "  `report_answer_num` bigint COMMENT '对回答举报的总数', \n" +
            "  `unhelp_answer_num` bigint COMMENT '对回答点无帮助的总数', \n" +
            "  `faved_answer_num` bigint COMMENT '对回答收藏的总数', \n" +
            "  `upvote_article_num` bigint COMMENT '对文章点赞总数', \n" +
            "  `report_article_num` bigint COMMENT '对文章举报的总数', \n" +
            "  `faved_article_num` bigint COMMENT '对文章收藏的总数', \n" +
            "  `comment_answer_num` bigint COMMENT '对回答评论的总数', \n" +
            "  `comment_article_num` bigint COMMENT '对文章评论的总数', \n" +
            "  `comment_question_num` bigint COMMENT '对问题评论的总数', \n" +
            "  `comment_pin_num` bigint COMMENT '对想法评论的总数', \n" +
            "  `comment_answer_len` bigint COMMENT '对回答评论的总字数', \n" +
            "  `comment_article_len` bigint COMMENT '对文章评论的总字数', \n" +
            "  `upvote_comment_num` bigint COMMENT '对评论点赞的总数', \n" +
            "  `downvote_comment_num` bigint COMMENT '对评论点反对的总数', \n" +
            "  `report_comment_num` bigint COMMENT '对评论举报的总数', \n" +
            "  `faved_pin_num` bigint COMMENT '对想法收藏的总数', \n" +
            "  `reaction_pin_num` bigint COMMENT '对想法鼓掌的总数', \n" +
            "  `receiver_invite_num` bigint COMMENT '收到邀请回答的总数', \n" +
            "  `follow_question_topic_num` bigint COMMENT '关注问题对应的话题数量', \n" +
            "  `follow_topic_domain_num` bigint COMMENT '关注的话题对应的领域', \n" +
            "  `statis_date` string COMMENT '日期 yyyy-MM-dd', \n" +
            "  `is_authenticated` bigint COMMENT '是否是认证用户', \n" +
            "  `pu_topic_ids` array<string> COMMENT '用户所擅长的话题集合', \n" +
            "  `address_ids` array<string> COMMENT '居住信息 id 集合', \n" +
            "  `address_names` array<string> COMMENT '居住地址集合', \n" +
            "  `education_ids` array<string> COMMENT '教育信息 id 集合', \n" +
            "  `education_names` array<string> COMMENT '教育信息名称集合', \n" +
            "  `business_ids` array<string> COMMENT '行业信息 id 集合', \n" +
            "  `business_names` array<string> COMMENT '行业信息名称集合', \n" +
            "  `employment_ids` array<string> COMMENT '工作信息 id 集合', \n" +
            "  `employment_names` array<string> COMMENT '工作信息名称集合', \n" +
            "  `account_status` bigint COMMENT '账号状态', \n" +
            "  `follow_column_num` bigint COMMENT '关注专栏数量', \n" +
            "  `answer_downvote_num` bigint COMMENT '回答被反对总数', \n" +
            "  `answer_thank_num` bigint COMMENT '回答被感谢总数', \n" +
            "  `answer_unhelp_num` bigint COMMENT '回答被点无帮助总数', \n" +
            "  `answer_report_num` bigint COMMENT '回答被举报总数', \n" +
            "  `article_report_num` bigint COMMENT '文章被举报总数', \n" +
            "  `article_payment_num` bigint COMMENT '文章被打赏总数', \n" +
            "  `answer_len` bigint COMMENT '回答文字总数', \n" +
            "  `article_len` bigint COMMENT '文章文字总数', \n" +
            "  `column_num` bigint COMMENT '专栏数量', \n" +
            "  `column_follow_num` bigint COMMENT '专栏被关注总数', \n" +
            "  `follow_favlist_num` bigint COMMENT '关注收藏夹数量', \n" +
            "  `repost_pin_num` bigint COMMENT '对想法转发数量', \n" +
            "  `uid` bigint COMMENT '用户 uid', \n" +
            "  `best_answerer_topic_id_set` array<string> COMMENT '优秀回答者的话题 id 集合', \n" +
            "  `became_best_answerer_topic_time` bigint COMMENT '成为话题下优秀回答者的时间', \n" +
            "  `first_marked_time` bigint COMMENT '第一次被标优的时间', \n" +
            "  `last_marked_time` bigint COMMENT '最近一次被标优的时间', \n" +
            "  `became_pu_time` bigint COMMENT '成为 pu 用户的时间', \n" +
            "  `became_verify_time` bigint COMMENT '认证时间', \n" +
            "  `zvideo_num` bigint COMMENT '创建的视频实体数', \n" +
            "  `zvideo_upvote_num` bigint COMMENT '视频实体被点赞数', \n" +
            "  `zvideo_downvote_num` bigint COMMENT '视频实体被反对数', \n" +
            "  `zvideo_comment_num` bigint COMMENT '视频实体被评论数', \n" +
            "  `zvideo_report_num` bigint COMMENT '视频实体被举报数', \n" +
            "  `zvideo_like_num` bigint COMMENT '视频实体被喜欢数', \n" +
            "  `upvote_zvideo_num` bigint COMMENT '对视频实体点赞的总数', \n" +
            "  `downvote_zvideo_num` bigint COMMENT '对视频实体反对的总数', \n" +
            "  `comment_zvideo_num` bigint COMMENT '对视频实体评论的总数', \n" +
            "  `report_zvideo_num` bigint COMMENT '对视频实体举报的总数', \n" +
            "  `like_zvideo_num` bigint COMMENT '对视频实体喜欢的总数', \n" +
            "  `is_creator` bigint COMMENT '是否是入住创作者', \n" +
            "  `video_num` bigint COMMENT '创建的视频数', \n" +
            "  `first_video_published_date` string COMMENT '首次发布视频时间', \n" +
            "  `first_zvideo_published_date` string COMMENT '首次发布视频实体时间', \n" +
            "  `actived_date` string COMMENT '激活时间', \n" +
            "  `image_hash` string COMMENT '图片的 hash', \n" +
            "  `avatar_path` string COMMENT '用户头像地址', \n" +
            "  `zvideo_favorite_num` bigint COMMENT '视频实体被收藏数', \n" +
            "  `last_video_published_date` string COMMENT '上次发布视频时间', \n" +
            "  `last_zvideo_published_date` string COMMENT '上次发布视频实体时间', \n" +
            "  `favorite_zvideo_num` bigint COMMENT '对视频实体的收藏数', \n" +
            "  `salt_score` bigint COMMENT '盐值分', \n" +
            "  `user_level` bigint COMMENT '用户等级', \n" +
            "  `creator_level` bigint COMMENT '创作者等级:1 级最低')\n" +
            "COMMENT '用户信息表: 包括了基础信息和聚合信息'\n" +
            "PARTITIONED BY ( \n" +
            "  `p_year` string COMMENT 'yyyy', \n" +
            "  `p_month` string COMMENT 'MM', \n" +
            "  `p_day` string COMMENT 'dd')\n" +
            "ROW FORMAT SERDE \n" +
            "  'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe' \n" +
            "STORED AS INPUTFORMAT \n" +
            "  'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat' \n" +
            "OUTPUTFORMAT \n" +
            "  'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat'\n" +
            "LOCATION\n" +
            "  'hdfs://hdfs01:8020/user/tc_warehouse/tables/dw_member.db/dw_member_pt_info'\n" +
            "TBLPROPERTIES (\n" +
            "  'last_modified_by'='hdfs', \n" +
            "  'last_modified_time'='1670991241', \n" +
            "  'spark.sql.create.version'='2.2 or prior', \n" +
            "  'spark.sql.sources.schema.numPartCols'='3', \n" +
            "  'spark.sql.sources.schema.numParts'='4', \n" +
            "  'spark.sql.sources.schema.part.0'='{\\\"type\\\":\\\"struct\\\",\\\"fields\\\":[{\\\"name\\\":\\\"member_id\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684 id\\\"}},{\\\"name\\\":\\\"hash_id\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684 hash id\\\"}},{\\\"name\\\":\\\"email\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684\\u90AE\\u7BB1\\\"}},{\\\"name\\\":\\\"phone_no\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684\\u624B\\u673A\\u53F7\\\"}},{\\\"name\\\":\\\"url_token\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684 url_token\\\"}},{\\\"name\\\":\\\"fullname\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684\\u5168\\u540D\\\"}},{\\\"name\\\":\\\"gender\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684\\u6027\\u522B\\\"}},{\\\"name\\\":\\\"headline\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684\\u7B7E\\u540D\\\"}},{\\\"name\\\":\\\"description\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684\\u81EA\\u6211\\u63CF\\u8FF0\\\"}},{\\\"name\\\":\\\"user_type\\\",\\\"type\\\":\\\"byte\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7C7B\\u578B: 0 \\u666E\\u901A\\u7528\\u6237 1 \\u673A\\u6784\\u8D26\\u53F7 2 \\u6E38\\u5BA2\\\"}},{\\\"name\\\":\\\"is_active\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u662F\\u5426\\u6FC0\\u6D3B\\\"}},{\\\"name\\\":\\\"is_pu\\\",\\\"type\\\":\\\"byte\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u662F\\u5426\\u662F pu \\u7528\\u6237\\\"}},{\\\"name\\\":\\\"is_best_answer\\\",\\\"type\\\":\\\"byte\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u662F\\u5426\\u662F\\u4F18\\u79C0\\u56DE\\u7B54\\u8005\\\"}},{\\\"name\\\":\\\"frequency\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8BBF\\u95EE\\u9891\\u7387\\\"}},{\\\"name\\\":\\\"loyalty\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6CE8\\u518C\\u5929\\u6570\\\"}},{\\\"name\\\":\\\"people_rank\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u6392\\u540D\\\"}},{\\\"name\\\":\\\"follower_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7C89\\u4E1D\\u6570\\\"}},{\\\"name\\\":\\\"followee_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5173\\u6CE8\\u7528\\u6237\\u6570\\\"}},{\\\"name\\\":\\\"follow_topic_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5173\\u6CE8\\u8BDD\\u9898\\u6570\\\"}},{\\\"name\\\":\\\"follow_question_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5173\\u6CE8\\u95EE\\u9898\\u6570\\\"}},{\\\"name\\\":\\\"answer_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7B54\\u6848\\u6570\\\"}},{\\\"name\\\":\\\"question_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u95EE\\u9898\\u6570\\\"}},{\\\"name\\\":\\\"article_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6587\\u7AE0\\u6570\\\"}},{\\\"name\\\":\\\"comment_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8BC4\\u8BBA\\u6570\\\"}},{\\\"name\\\":\\\"hold_live_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u4E3B\\u8BB2 Live \\u573A\\u6B21\\\"}},{\\\"name\\\":\\\"pin_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u53D1 pin \\u7684\\u6570\\u91CF\\\"}},{\\\"name\\\":\\\"public_fav_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u521B\\u5EFA\\u7684\\u516C\\u5F00\\u6536\\u85CF\\u5939\\u6570\\\"}},{\\\"name\\\":\\\"answer_voted_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7B54\\u6848\\u83B7\\u5F97\\u7684\\u70B9\\u8D5E\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"article_voted_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6587\\u7AE0\\u83B7\\u5F97\\u7684\\u70B9\\u8D5E\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"pin_voted_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"pin \\u83B7\\u5F97\\u7684\\u70B9\\u8D5E\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"answer_faved_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7B54\\u6848\\u88AB\\u6536\\u85CF\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"article_faved_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6587\\u7AE0\\u88AB\\u6536\\u85CF\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"answer_commented_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7B54\\u6848\\u88AB\\u8BC4\\u8BBA\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"article_commented_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6587\\u7AE0\\u88AB\\u8BC4\\u8BBA\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"vote_answer_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u7B54\\u6848\\u70B9\\u8D5E\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"down_vote_answer_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u7B54\\u6848\\u70B9\\u53CD\\u5BF9\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"thank_answer_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u7B54\\u6848\\u611F\\u8C22\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"vote_pin_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9 pin \\u70B9\\u8D5E\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"invites\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u9080\\u8BF7\\u522B\\u4EBA\\u56DE\\u7B54\\u6B21\\u6570\\\"}},{\\\"name\\\":\\\"live_order_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8D2D\\u4E70 live \\u6570\\\"}},{\\\"name\\\":\\\"book_order_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8D2D\\u4E70\\u7535\\u5B50\\u4E66\\u6570\\\"}},{\\\"name\\\":\\\"article_order_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8D5E\\u8D4F\\u6587\\u7AE0\\u6570\\\"}},{\\\"name\\\":\\\"live_amount\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8D2D\\u4E70 live \\u603B\\u94B1\\u6570\\\"}},{\\\"name\\\":\\\"book_amount\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8D2D\\u4E70\\u7535\\u5B50\\u4E66\\u603B\\u94B1\\u6570\\\"}},{\\\"name\\\":\\\"article_amount\\\",\\\"ty', \n" +
            "  'spark.sql.sources.schema.part.1'='pe\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8D5E\\u8D4F\\u6587\\u7AE0\\u7684\\u603B\\u94B1\\u6570\\\"}},{\\\"name\\\":\\\"reg_source\\\",\\\"type\\\":\\\"byte\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u6CE8\\u518C\\u6765\\u6E90\\\"}},{\\\"name\\\":\\\"reg_type\\\",\\\"type\\\":\\\"byte\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u6CE8\\u518C\\u7C7B\\u578B\\\"}},{\\\"name\\\":\\\"modified_at\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u4FEE\\u6539\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"modified_date\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u4FEE\\u6539\\u65F6\\u95F4: yyyy-MM-dd\\\"}},{\\\"name\\\":\\\"created_at\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684\\u6CE8\\u518C\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"created_date\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7684\\u6CE8\\u518C\\u65F6\\u95F4: yyyy-MM-dd\\\"}},{\\\"name\\\":\\\"marked_answer_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u88AB\\u6807\\u4F18\\u7684\\u7B54\\u6848\\u6570\\\"}},{\\\"name\\\":\\\"marked_article_num\\\",\\\"type\\\":\\\"integer\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u88AB\\u6807\\u4F18\\u7684\\u6587\\u7AE0\\u6570\\\"}},{\\\"name\\\":\\\"medal_id_info\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"long\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5FBD\\u7AE0id\\u4FE1\\u606F\\\"}},{\\\"name\\\":\\\"report_answer_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u56DE\\u7B54\\u4E3E\\u62A5\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"unhelp_answer_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u56DE\\u7B54\\u70B9\\u65E0\\u5E2E\\u52A9\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"faved_answer_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u56DE\\u7B54\\u6536\\u85CF\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"upvote_article_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u6587\\u7AE0\\u70B9\\u8D5E\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"report_article_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u6587\\u7AE0\\u4E3E\\u62A5\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"faved_article_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u6587\\u7AE0\\u6536\\u85CF\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"comment_answer_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u56DE\\u7B54\\u8BC4\\u8BBA\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"comment_article_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u6587\\u7AE0\\u8BC4\\u8BBA\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"comment_question_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u95EE\\u9898\\u8BC4\\u8BBA\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"comment_pin_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u60F3\\u6CD5\\u8BC4\\u8BBA\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"comment_answer_len\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u56DE\\u7B54\\u8BC4\\u8BBA\\u7684\\u603B\\u5B57\\u6570\\\"}},{\\\"name\\\":\\\"comment_article_len\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u6587\\u7AE0\\u8BC4\\u8BBA\\u7684\\u603B\\u5B57\\u6570\\\"}},{\\\"name\\\":\\\"upvote_comment_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u8BC4\\u8BBA\\u70B9\\u8D5E\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"downvote_comment_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u8BC4\\u8BBA\\u70B9\\u53CD\\u5BF9\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"report_comment_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u8BC4\\u8BBA\\u4E3E\\u62A5\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"faved_pin_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u60F3\\u6CD5\\u6536\\u85CF\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"reaction_pin_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u60F3\\u6CD5\\u9F13\\u638C\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"receiver_invite_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6536\\u5230\\u9080\\u8BF7\\u56DE\\u7B54\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"follow_question_topic_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5173\\u6CE8\\u95EE\\u9898\\u5BF9\\u5E94\\u7684\\u8BDD\\u9898\\u6570\\u91CF\\\"}},{\\\"name\\\":\\\"follow_topic_domain_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5173\\u6CE8\\u7684\\u8BDD\\u9898\\u5BF9\\u5E94\\u7684\\u9886\\u57DF\\\"}},{\\\"name\\\":\\\"statis_date\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u65E5\\u671F yyyy-MM-dd\\\"}},{\\\"name\\\":\\\"is_authenticated\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u662F\\u5426\\u662F\\u8BA4\\u8BC1\\u7528\\u6237\\\"}},{\\\"name\\\":\\\"pu_topic_ids\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u6240\\u64C5\\u957F\\u7684\\u8BDD\\u9898\\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"address_ids\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5C45\\u4F4F\\u4FE1\\u606F id \\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"address_names\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5C45\\u4F4F\\u5730\\u5740\\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"education_ids\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6559\\u80B2\\u4FE1\\u606F id \\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"education_names\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6559\\u80B2\\u4FE1\\u606F\\u540D\\u79F0\\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"business_ids\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u884C\\u4E1A\\u4FE1\\u606F id \\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"business_names\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u884C\\u4E1A', \n" +
            "  'spark.sql.sources.schema.part.2'='\\u4FE1\\u606F\\u540D\\u79F0\\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"employment_ids\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5DE5\\u4F5C\\u4FE1\\u606F id \\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"employment_names\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5DE5\\u4F5C\\u4FE1\\u606F\\u540D\\u79F0\\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"account_status\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8D26\\u53F7\\u72B6\\u6001\\\"}},{\\\"name\\\":\\\"follow_column_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5173\\u6CE8\\u4E13\\u680F\\u6570\\u91CF\\\"}},{\\\"name\\\":\\\"answer_downvote_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u56DE\\u7B54\\u88AB\\u53CD\\u5BF9\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"answer_thank_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u56DE\\u7B54\\u88AB\\u611F\\u8C22\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"answer_unhelp_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u56DE\\u7B54\\u88AB\\u70B9\\u65E0\\u5E2E\\u52A9\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"answer_report_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u56DE\\u7B54\\u88AB\\u4E3E\\u62A5\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"article_report_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6587\\u7AE0\\u88AB\\u4E3E\\u62A5\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"article_payment_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6587\\u7AE0\\u88AB\\u6253\\u8D4F\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"answer_len\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u56DE\\u7B54\\u6587\\u5B57\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"article_len\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6587\\u7AE0\\u6587\\u5B57\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"column_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u4E13\\u680F\\u6570\\u91CF\\\"}},{\\\"name\\\":\\\"column_follow_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u4E13\\u680F\\u88AB\\u5173\\u6CE8\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"follow_favlist_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5173\\u6CE8\\u6536\\u85CF\\u5939\\u6570\\u91CF\\\"}},{\\\"name\\\":\\\"repost_pin_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u60F3\\u6CD5\\u8F6C\\u53D1\\u6570\\u91CF\\\"}},{\\\"name\\\":\\\"uid\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237 uid\\\"}},{\\\"name\\\":\\\"best_answerer_topic_id_set\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"elementType\\\":\\\"string\\\",\\\"containsNull\\\":true},\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u4F18\\u79C0\\u56DE\\u7B54\\u8005\\u7684\\u8BDD\\u9898 id \\u96C6\\u5408\\\"}},{\\\"name\\\":\\\"became_best_answerer_topic_time\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6210\\u4E3A\\u8BDD\\u9898\\u4E0B\\u4F18\\u79C0\\u56DE\\u7B54\\u8005\\u7684\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"first_marked_time\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7B2C\\u4E00\\u6B21\\u88AB\\u6807\\u4F18\\u7684\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"last_marked_time\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6700\\u8FD1\\u4E00\\u6B21\\u88AB\\u6807\\u4F18\\u7684\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"became_pu_time\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6210\\u4E3A pu \\u7528\\u6237\\u7684\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"became_verify_time\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u8BA4\\u8BC1\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"zvideo_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u521B\\u5EFA\\u7684\\u89C6\\u9891\\u5B9E\\u4F53\\u6570\\\"}},{\\\"name\\\":\\\"zvideo_upvote_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u89C6\\u9891\\u5B9E\\u4F53\\u88AB\\u70B9\\u8D5E\\u6570\\\"}},{\\\"name\\\":\\\"zvideo_downvote_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u89C6\\u9891\\u5B9E\\u4F53\\u88AB\\u53CD\\u5BF9\\u6570\\\"}},{\\\"name\\\":\\\"zvideo_comment_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u89C6\\u9891\\u5B9E\\u4F53\\u88AB\\u8BC4\\u8BBA\\u6570\\\"}},{\\\"name\\\":\\\"zvideo_report_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u89C6\\u9891\\u5B9E\\u4F53\\u88AB\\u4E3E\\u62A5\\u6570\\\"}},{\\\"name\\\":\\\"zvideo_like_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u89C6\\u9891\\u5B9E\\u4F53\\u88AB\\u559C\\u6B22\\u6570\\\"}},{\\\"name\\\":\\\"upvote_zvideo_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u89C6\\u9891\\u5B9E\\u4F53\\u70B9\\u8D5E\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"downvote_zvideo_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u89C6\\u9891\\u5B9E\\u4F53\\u53CD\\u5BF9\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"comment_zvideo_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u89C6\\u9891\\u5B9E\\u4F53\\u8BC4\\u8BBA\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"report_zvideo_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u89C6\\u9891\\u5B9E\\u4F53\\u4E3E\\u62A5\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"like_zvideo_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u89C6\\u9891\\u5B9E\\u4F53\\u559C\\u6B22\\u7684\\u603B\\u6570\\\"}},{\\\"name\\\":\\\"is_creator\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u662F\\u5426\\u662F\\u5165\\u4F4F\\u521B\\u4F5C\\u8005\\\"}},{\\\"name\\\":\\\"video_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u521B\\u5EFA\\u7684\\u89C6\\u9891\\u6570\\\"}},{\\\"name\\\":\\\"first_video_published_date\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u9996\\u6B21\\u53D1\\u5E03\\u89C6\\u9891\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"first_zvideo_published_date\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u9996\\u6B21\\u53D1\\u5E03\\u89C6\\u9891\\u5B9E\\u4F53\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"actived_date\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u6FC0\\u6D3B\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"image_hash\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u56FE\\u7247\\u7684 hash\\\"}},{\\\"name\\\":\\\"avatar_path\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u5934\\u50CF\\u5730\\u5740\\\"}},{\\\"name\\\":\\\"zvideo_favorite_num\\\",\\\"type\\\":\\\"long\\\",\\\"nulla', \n" +
            "  'spark.sql.sources.schema.part.3'='ble\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u89C6\\u9891\\u5B9E\\u4F53\\u88AB\\u6536\\u85CF\\u6570\\\"}},{\\\"name\\\":\\\"last_video_published_date\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u4E0A\\u6B21\\u53D1\\u5E03\\u89C6\\u9891\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"last_zvideo_published_date\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u4E0A\\u6B21\\u53D1\\u5E03\\u89C6\\u9891\\u5B9E\\u4F53\\u65F6\\u95F4\\\"}},{\\\"name\\\":\\\"favorite_zvideo_num\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u5BF9\\u89C6\\u9891\\u5B9E\\u4F53\\u7684\\u6536\\u85CF\\u6570\\\"}},{\\\"name\\\":\\\"salt_score\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u76D0\\u503C\\u5206\\\"}},{\\\"name\\\":\\\"user_level\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u7528\\u6237\\u7B49\\u7EA7\\\"}},{\\\"name\\\":\\\"creator_level\\\",\\\"type\\\":\\\"long\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"\\u521B\\u4F5C\\u8005\\u7B49\\u7EA7:1 \\u7EA7\\u6700\\u4F4E\\\"}},{\\\"name\\\":\\\"p_year\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"yyyy\\\"}},{\\\"name\\\":\\\"p_month\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"MM\\\"}},{\\\"name\\\":\\\"p_day\\\",\\\"type\\\":\\\"string\\\",\\\"nullable\\\":true,\\\"metadata\\\":{\\\"comment\\\":\\\"dd\\\"}}]}', \n" +
            "  'spark.sql.sources.schema.partCol.0'='p_year', \n" +
            "  'spark.sql.sources.schema.partCol.1'='p_month', \n" +
            "  'spark.sql.sources.schema.partCol.2'='p_day', \n" +
            "  'transient_lastDdlTime'='1670991241')";

    static final String SQL_SET_INSERT = "set mapreduce.job.queuename=123;";
}
