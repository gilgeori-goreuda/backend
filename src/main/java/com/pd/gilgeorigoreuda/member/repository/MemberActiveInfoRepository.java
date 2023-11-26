package com.pd.gilgeorigoreuda.member.repository;

import com.pd.gilgeorigoreuda.member.domain.entity.MemberActiveInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberActiveInfoRepository extends JpaRepository<MemberActiveInfo, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update member_active_info m " +
            "join(select " +
            "member_id, sum(walking_distance) as total_walking_distance " +
            "from store_visit_records group by member_id) " +
            "s on m.member_id = s.member_id " +
            "set m.total_walking_distance = s.total_walking_distance", nativeQuery = true)
    void updateMemberTotalDistance();

    @Modifying(clearAutomatically = true)
    @Query(value = "update member_active_info m " +
            "join(select " +
            "member_id, count(*) as total_visit_count " +
            "from store_visit_records where is_visited = true " +
            "group by member_id) " +
            "s on m.member_id = s.member_id " +
            "set m.total_visit_count = s.total_visit_count", nativeQuery = true)
    void updateMemberTotalVisitCount();

    @Modifying(clearAutomatically = true)
    @Query(value = "update member_active_info " +
            "set exp = round(total_walking_distance * :value, 1) + total_visit_count", nativeQuery = true)
    void updateMemberExp(@Param("value") double value);

    @Modifying(clearAutomatically = true)
    @Query(value = "update member_active_info " +
            "set exp = " +
            "case " +
            "when exp >= 0 AND exp <= 10 THEN 'BEGINNER' " +
            "WHEN exp >= 11 AND exp <= 20 THEN 'INTERMEDIATE' " +
            "WHEN exp >= 21 THEN 'EXPERT' " +
            "end;", nativeQuery = true)
    void updateMemberLevel();
}
