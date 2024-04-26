package com.example.repository;

public class SqlQuery {

    public static final String SELECT_COUNT_DROP_BY_DROPID = "SELECT COUNT(*) FROM dropid WHERE :INid = dropid.dropid";

    public static final String SELECT_COUNT_BY_CASE_ID_AND_STATUS_9 =
            "SELECT COUNT(1) FROM PICKDETAIL " +
                    "WHERE CaseID = :INid AND STATUS < 9";

    public static final String SELECT_PARAMS_FROM_PICKDETAIL_ORDERS_SKU =
            "SELECT pickdetail.sku, " +
                    "SKU.DESCR, " +
                    "pickdetail.storerkey, " +
                    "pickdetail.lot, " +
                    "pickdetail.qty, " +
                    "pickdetail.id, " +
                    "pickdetail.loc, " +
                    "pickdetail.toloc, " +
                    "orders.consigneekey, " +
                    "orders.door, " +
                    "pickdetail.UOM " +
                    "FROM PICKDETAIL, ORDERS, SKU " +
                    "WHERE PICKDETAIL.StorerKey = SKU.StorerKey " +
                    "AND PICKDETAIL.SKU = SKU.SKU " +
                    "AND PICKDETAIL.CaseID = :caseId " +
                    "AND PICKDETAIL.OrderKey = ORDERS.OrderKey";

    public static final String SELECT_MAX_TO_LOC_AND_LOC =
            "SELECT MAX(ToLoc), MAX(Loc) " +
                    "FROM PICKDETAIL " +
                    "WHERE CaseID = :InID " +
                    "AND Status < '9'";

    public static final String SELECT_CUSTOMER_AND_DOOR =
            "SQL SELECT DISTINCT ORDERS.ConsigneeKey, ORDERS.Door " +
                    "FROM Orders, PICKDETAIL " +
                    "WHERE PICKDETAIL.CaseID = :InID " +
                    "AND PICKDETAIL.OrderKey = Orders.OrderKey";

    public static final String SELECT_BY_CHILD_ID =
            "SELECT xpickdetail.sku, " +
                    "SKU.DESCR, " +
                    "xpickdetail.storerkey, " +
                    "xpickdetail.lot, " +
                    "xpickdetail.qty, " +
                    "xpickdetail.id, " +
                    "xorders.consigneekey, " +
                    "xpickdetail.UOM " +
                    "FROM xpickdetail, xorders, SKU " +
                    "WHERE xpickdetail.StorerKey = SKU.StorerKey " +
                    "AND xpickdetail.SKU = SKU.SKU " +
                    "AND childId = xpickdetail.caseid " +
                    "AND xpickdetail.orderkey = xorders.orderkey";

    public static final String SELECT_DETAIL_FROM_TRANSSHIP =
            "SELECT CustomerKey, Qty" +
                    "FROM Transship " +
                    "WHERE childId = Transship.ContainerID";

    public static final String SELECT_LANES_BY_CONSIGNEE_KEY =
            "SELECT lane.lanekey " +
                    "FROM lane " +
                    "WHERE :consigneeKey = lane.laneassg1 " +
                    "AND Lane.Lanetype IN '2'";

    public static final String SELECT_DROPIDDETAIL_BY_DROPID_ORDER_BY_EDITDATE_DESC =
            "SELECT dropiddetail.Dropid, dropiddetail.Childid, dropiddetail.IDType, dropid.droploc " +
                    "FROM dropiddetail, dropid " +
                    "WHERE :INid = dropiddetail.dropid AND dropiddetail.dropid = dropid.dropid " +
                    "ORDER BY DropIDDetail.EditDate DESC";

    public static final String SELECT_DROPID_STATUS_BY_DROPID = "SELECT status FROM dropid WHERE :INid = dropid.dropid";

    public static final String SELECT_TASK_DETAILS_BY_TO_ID_STATUS_0_MV = "SELECT TaskDetail.CaseID, TaskDetail.StorerKey, TaskDetail.Sku, SKU.descr, TaskDetail.Lot, TaskDetail.FromID, TaskDetail.FromLoc, TaskDetail.ToID, TaskDetail.toloc, TaskDetail.qty, TaskDetail.UOM " +
            "FROM TaskDetail, SKU " +
            "WHERE TaskDetail.StorerKey = SKU.StorerKey " +
            "AND TaskDetail.SKU = SKU.SKU " +
            "AND ToID = :movableUnit " +
            "AND Status = ''0'' " +
            "AND TaskType = ''MV'''";
}
