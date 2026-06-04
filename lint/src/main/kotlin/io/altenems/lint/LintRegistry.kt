package io.altenems.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import io.altenems.lint.annotation.NamedAnnotationDetector
import io.altenems.lint.room.CoroutineDaoFunctionsIssue
import io.altenems.lint.serialization.MissingSerializableAnnotationIssue
import io.altenems.lint.webview.EvaluateJavascriptDetector

class LintRegistry : IssueRegistry() {
    override val issues: List<Issue> = listOf(
        MissingSerializableAnnotationIssue.ISSUE,
        MissingSerializableAnnotationIssue.RECOMMENDATION,
        CoroutineDaoFunctionsIssue.ISSUE,
        NamedAnnotationDetector.ISSUE,
        EvaluateJavascriptDetector.ISSUE,
    )

    override val api: Int = CURRENT_API

    override val vendor: Vendor = Vendor(
        vendorName = "Alten",
        feedbackUrl = "https://github.com/home-assistant/android/issues",
        contact = "https://github.com/home-assistant/android/",
    )
}
