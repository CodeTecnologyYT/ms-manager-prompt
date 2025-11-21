### 📝 Question Sent to LLM

**User Query:** ${question}

<#-- Block of extracted terms -->
<#if extractedCandidates?size gt 0 >
### 📋 Extracted Candidate Terms (${extractedCandidates?size} terms)
These are all biomedical terms extracted from your query:
<#list extractedCandidates as c> 
- **${c}**
</#list>
<#if extractedCandidates?size gt 20>
*...and ${extractedCandidates?size - 20} more terms*
</#if>
</#if>

<#-- Block of entities >
<#if entities?size gt 0 >
### ✅ Matched Entities ({{entities?size}}) found in graph
These extracted terms were successfully matched to graph nodes:

| Entity Name | Labels | Query Term |
|-------------|--------|------------|
<#list entities as e>
| <#if e.name??>${e.name}<#else>N/A</#if> | <#list e.labels as l> ${l}, </#list>| ${e.id} |
</#list>
<#else>
### ✅ Matched Entities
*No entities matched to graph nodes.*
</#if >

<#-- Block of unmatched terms -->
<#if unmatchedCandidates?size gt 0 >
### ⚠️ Unmatched Entities (${unmatchedCandidates?size}) not found in graph

These extracted terms were **NOT** matched to graph nodes. They may not exist in the knowledge graph, or may need
different spelling (e.g., BRAC1 vs BRCA1):
<#list unmatchedCandidates as c> 
- **${c}**
</#list>
<#if unmatchedCandidates?size gt 20>
  *...and ${unmatchedCandidates?size - 20} an unmatched more terms*
</#if>
</#if>

<#-- Block of chunks -->
<#if chunk?size gt 0 >
### 📚 Retrieved Text Chunks (${chunk?size} total)
<#if chunkPatterns.together gt 0>
#### 📦 Chunks with All Entities Together (${chunkPatterns.together} chunks)
<#list chunkTogether as c>
**Together Chunk ${c?index}** (ID: `${c.chunkId}`)
**Content:**
```
${c.text}
```
</#list >
</#if>

<#if chunkPatterns.pairs gt 0 >
#### 👥 Chunks with Entity Pairs ({{chunkPatterns.pairs}} chunks)
</#if>

<#if chunkPatterns.individual gt 0 >
#### 👤 Chunks with Individual Entities({{chunkPatterns.individual}} chunks)
</#if>

#### 🔍 Other Relevant Chunks (from vector search) (${chunk?size} chunks)
<#list chunkOthers as c >
**Together Chunk ${c?index}** (ID: `${c.chunkId}`)
**Content:**
```
${c.text}
```
</#list>
<#else>
### 📚 Retrieved Text Chunks
*No chunks retrieved.*
</#if>

<#-- Block of triplets -->
<#if triplets?size gt 0 >
### 🔗 Entity Relationship Triplets (${triplets?size} relationships)
This shows all relationships found in the graph involving the **matched entities**:
<#list entities as e >
<#if e.name??> - ${e.name}<#else>N/A</#if>
</#list>

| Source Entity | Source Labels | Source Properties | Relationship | Target Entity | Target Labels | Target Properties |
|--------------|-------------------|--------------|--------------|------------------|------------------|------------------|
<#list triplets as t>
|${t.sourceName} |<#list t.sourceLabels as l> ${l}, </#list>|<#if t.sourceProperties.id??>${t.sourceProperties.id}<#else> N/A </#if>| ${t.relationship} | ${t.targetName} |<#list t.targetLabels as l> ${l}, </#list>|<#if t.targetProperties.id??>${t.targetProperties.id}<#else>N/A</#if> |
</#list>
<#if triplets?size gt 20 >
*...and {${triplets?size} - 20} more relationships*
</#if>
<#else>
### 🔗 Entity Relationship Triplets
*No triplets retrieved. This shows all relationships involving the extracted entities.*
</#if>