select
	/*%expand*/*
from todo
where
/*%if completed != null */
	completed = /* completed */true
/*%end */
