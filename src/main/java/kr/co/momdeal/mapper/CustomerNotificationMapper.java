package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CustomerNotificationVO;

@MapperScan
public interface CustomerNotificationMapper {
	Page<CustomerNotificationVO> selectCUNList(CustomerNotificationVO cun);
	CustomerNotificationVO selectCUN(int cunNum);
	int updateCUN(CustomerNotificationVO cun);
	int insertCUN(CustomerNotificationVO cun);
	int deleteCUN(int cunNum);
}