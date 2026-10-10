fun main() {
    val nums1 = listOf(0, 2, 2)
    val nums2 = listOf(1, 3)
    val nums3 = mergeSlices(nums1, nums2)
    println(nums3.toList())
}

fun mergeSlices(nums1: List<Int>, nums2: List<Int>) : List<Int>{
    val res = ArrayList<Int>(nums1.size + nums2.size)

    var i = 0
    var j = 0

    while (i < nums1.size && j < nums2.size){
        if (nums1[i] <= nums2[j]){
            res.add(nums1[i])
            i++
        } else {
            res.add(nums2[j])
            j++
        }
    }

    while (i < nums1.size){
        res.add(nums1[i])
        i++
    }

    while (j < nums2.size){
        res.add(nums2[j])
        j++
    }

    return res
}
